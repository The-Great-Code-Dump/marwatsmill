package uk.tojourn.twitterstreamkafka.twitter

import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.io.InputStreamReader
import org.springframework.boot.context.properties.ConfigurationProperties

import java.io.BufferedReader

private val logger = KotlinLogging.logger {}

@Component
class StreamConsumer(private val twitterConfig: TwitterConfig) {
    private val client = HttpClients.createDefault()

    @EventListener(classes = [ApplicationReadyEvent::class])
    fun setUpRules() {
        val httpPost = HttpPost(twitterConfig.url + "/rules")

        // @Shelby TODO supply twitter rules from json as a list of rules that can be posted at once
        httpPost.entity = StringEntity(
            twitterConfig.rules
        )
        httpPost.addHeader("Authorization", "Bearer ${twitterConfig.bearerToken}")
        httpPost.addHeader(
            "Cookie",
            "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609"
        )
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        client.execute(httpPost).use {
            if (it.statusLine.statusCode !in HttpStatus.SC_OK..299) {
                logger.error { "Its gone pete tong" }
            }
            it
        }
    }

    fun connectStream(): BufferedReader {
        val httpGet = HttpGet(twitterConfig.url)
        httpGet.addHeader("Authorization", "Bearer ${twitterConfig.bearerToken}");
        httpGet.addHeader(
            "Cookie",
            "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609"
        )
        httpGet.setHeader("Accept", "application/json");
        httpGet.setHeader("Connection", "keep-alive");
        val response: HttpResponse = client.execute(httpGet)
        val entity: HttpEntity = response.entity

        return BufferedReader(InputStreamReader(entity.content))
    }

}
