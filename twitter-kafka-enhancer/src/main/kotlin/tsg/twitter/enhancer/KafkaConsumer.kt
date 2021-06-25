package tsg.twitter.enhancer

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import tsg.twitter.enhancer.dto.EnhancedTweet

private val logger = KotlinLogging.logger {}

@Component
class KafkaConsumer(val twitterService: TwitterService, val kafkaTemplate: KafkaTemplate<String, EnhancedTweet>) {

    @KafkaListener(topics = ["tweets"], groupId = "test-consumer-group")
    fun listen(@Payload tweet: String) {
        logger.info { "Twitter ID : \"$tweet\"" }

        try {
            //twittered handles rate limiting for us.
            val enhancedTweet = twitterService.getEnhancedTweet(tweet)
            logger.info { enhancedTweet }

            kafkaTemplate.send("enhanced-tweets", enhancedTweet)
        } catch (e: Exception) {
            val responseAsString = twitterService.getEnhancedTweetAsString(tweet)
            logger.error(e) { responseAsString }
        }
    }

}