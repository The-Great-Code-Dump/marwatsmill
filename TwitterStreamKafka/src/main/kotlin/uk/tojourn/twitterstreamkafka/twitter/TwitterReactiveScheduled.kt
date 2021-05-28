package uk.tojourn.twitterstreamkafka.twitter

import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import uk.tojourn.twitterstreamkafka.config.KafkaConfig
import uk.tojourn.twitterstreamkafka.config.TwitterConfig

private val logger = KotlinLogging.logger { }

@Component
class TwitterReactiveScheduled(val twitterConfig: TwitterConfig, val kafkaConfig: KafkaConfig, val twitterRepo: TwitterReactiveRepository, val ruleService: RuleService, val kafkaTemplate: KafkaTemplate<String, String>) {

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(0)
    fun setUpRules() {
        ruleService.cleanExistingRules()
        ruleService.createRules(twitterConfig)
    }

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(1)
    fun processTweets() {
        twitterRepo.getTweets()
                .subscribe{ twitterResponse ->
                    logger.info { "Sending message to shit hot tweets $twitterResponse" }
                    kafkaTemplate.send(kafkaConfig.topics.twitter, twitterResponse.data.id.toString())
                }
    }
}