package tsg.twitter.enhancer

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
            //twittered handles rate limiting for us.
            val superTweet = twitterService.getEnhancedTweet(tweet)
            logger.info { superTweet }

        } catch (e: Exception) {
            val responseAsString = twitterService.getEnhancedTweetAsString(tweet)
            logger.error(e) { responseAsString }
        }
    }

}