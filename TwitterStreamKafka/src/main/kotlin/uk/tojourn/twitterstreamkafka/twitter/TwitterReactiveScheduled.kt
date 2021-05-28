package uk.tojourn.twitterstreamkafka.twitter

import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }

@Component
class TwitterReactiveScheduled(val config: TwitterConfig, val twitterRepo: TwitterReactiveRepository, val ruleService: RuleService) {

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(0)
    fun setUpRules() {
        ruleService.cleanExistingRules()
        ruleService.createRules(config)
    }

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(1)
    fun processTweets() {
        twitterRepo
                .getTweets()
                .doOnNext { uk.tojourn.twitterstreamkafka.twitter.logger.info { "TwitterResponse: $it" } }
                .subscribe()
    }
}