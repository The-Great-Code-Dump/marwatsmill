package tsg.twitter.twitterstreamkafka.twitter

import com.fasterxml.jackson.databind.JsonNode
import mu.KotlinLogging
import org.json.JSONArray
import org.json.JSONObject
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import tsg.twitter.twitterstreamkafka.config.TwitterConfig

private val logger = KotlinLogging.logger { }

val createRulesFunction: (Map<String, String>) -> String = { rules ->
    val ruleArray: List<JSONObject> = rules.map {
        JSONObject().put("value", "(${it.value}) lang:en").put("tag", it.key)
    }

    JSONObject().put("add", JSONArray(ruleArray)).toString()
}

@Service
class RuleService(val webClient: WebClient, val config: TwitterConfig) {

    fun createRules(config: TwitterConfig) = webClient.post()
            .uri(config.url + "/rules")
            .body(BodyInserters.fromValue(createRulesFunction(config.rules).trimMargin()))
            .retrieve()
            .bodyToMono<String>()
            .block()

    fun cleanExistingRules() {
        val existingRuleIds = webClient.get()
                .uri("${config.url}/rules")
                .retrieve()
                .bodyToMono<JsonNode>()
                .block()
                ?.get("data")?.map {
                    logger.info { it }
                    it
                }?.mapNotNull { it?.get("id")?.textValue() }.orEmpty()


        if(existingRuleIds.isNotEmpty()) {
            val deleteRuleRequestBody = JSONObject().put("delete", JSONObject().put("ids", JSONArray(existingRuleIds) ))

            webClient.post()
                .uri(config.url + "/rules")
                .body(BodyInserters.fromValue(deleteRuleRequestBody.toString()))
                .retrieve()
                .bodyToMono<String>()
                .block()
        }
    }
}