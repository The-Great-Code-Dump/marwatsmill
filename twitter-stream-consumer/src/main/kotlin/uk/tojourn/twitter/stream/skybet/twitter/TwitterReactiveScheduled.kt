package uk.tojourn.twitter.stream.skybet.twitter

import TwitterConfig
import io.micrometer.core.instrument.Tag
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
        //ruleService.showExistingRules()
        ruleService.createRules(twitterConfig)
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
            rule.tag == "skybet-media" ||
            rule.tag == "skybet-stopped" ||
            rule.tag == "skybet-loading" ||
            rule.tag == "skybet-down" ||
            rule.tag == "skybet-banned" ||
            rule.tag == "skybet-help-media" ||
            rule.tag == "skybet-help-stopped" ||
            rule.tag == "skybet-help-loading" ||
            rule.tag == "skybet-help-down" ||
            rule.tag == "skybet-help-banned" ||
            rule.tag == "matt-media" ||
            rule.tag == "matt-stopped" ||
            rule.tag == "matt-loading" ||
            rule.tag == "matt-down" ||
            rule.tag == "matt-banned"
        }
        if (matchingRule != null) {
            metrics.incrementMatchingTweetRules(matchingRule.tag)

        }
    }
}