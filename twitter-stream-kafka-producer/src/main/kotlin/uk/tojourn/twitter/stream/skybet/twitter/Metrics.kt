package uk.tojourn.twitter.stream.skybet.twitter

import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class Metrics(private val meterRegistry: MeterRegistry) {

    fun incrementMatchingTweetRules(tag: String) =
        meterRegistry.counter("tweet.matching.rule", tag).increment()
}