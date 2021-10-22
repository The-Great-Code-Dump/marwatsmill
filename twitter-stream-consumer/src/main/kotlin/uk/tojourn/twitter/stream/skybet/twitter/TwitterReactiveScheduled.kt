package uk.tojourn.twitter.stream.skybet.twitter

import TwitterConfig
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component


private val logger = KotlinLogging.logger { }

@Component
class TwitterReactiveScheduled(
    val twitterConfig: TwitterConfig,
    val twitterRepo: TwitterReactiveRepository,
    val ruleService: RuleService,
    val metrics: Metrics
) {

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(0)
    fun setUpRules() {
        ruleService.showExistingRules()
        //ruleService.createRules(twitterConfig)
    }

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(1)
    fun processTweets() {
        logger.info { "Listening for tweets with your rules..." }
        twitterRepo.getTweets()
            .doOnError { error ->
            logger.error { "Something went pete tong. ${error.cause} \n ${error.stackTrace}" }
        }
            .subscribe { twitterResponse ->
                logger.info { twitterResponse }
                updateMetrics(twitterResponse)
            }
    }

    private fun updateMetrics(twitterResponse: TwitterResponse) {
        val matchingRule = twitterResponse.matchingRules.find { rule ->
            rule.tag == "media-query" ||
                    rule.tag == "stopped-query" ||
                    rule.tag == "loading-query" ||
                    rule.tag == "down-query" ||
                    rule.tag == "banned-query"
        }
        if (matchingRule != null){
            metrics.incrementMatchingTweetRules(matchingRule.tag)
        }
    }
}