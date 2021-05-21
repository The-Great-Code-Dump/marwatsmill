package uk.tojourn.twitterstreamkafka.twitter

import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class StreamProcessor(val twitterRepo: TwitterReactiveRepository) {
   @EventListener(classes = [ApplicationReadyEvent::class])
   @Order(1)
    fun processTweets() {
        twitterRepo
            .getTweets()
            .doOnNext { logger.info { "TwitterResponse: $it" } }
            .subscribe()
    }
}