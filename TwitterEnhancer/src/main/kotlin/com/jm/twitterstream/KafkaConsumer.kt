package com.jm.twitterstream

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class KafkaConsumer(val twitterService: TwitterService) {

    @KafkaListener(topics = ["tweets"], groupId = "test-consumer-group")
    fun listen(@Payload tweet: String) {
        logger.info { "Twitter ID : \"$tweet\"" }

        try {
        val superTweet = twitterService.getEnhancedTweet(tweet)

        logger.info { superTweet }

        Thread.sleep(15000L)
        } catch (e: Exception) {
            logger.error(e) { "Oooops"  }
            Thread.sleep(15000L)
        }
    }

}