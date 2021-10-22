package uk.tojourn.twitter.stream.skybet

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.kotest.assertions.asClue
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import uk.tojourn.twitter.stream.skybet.twitter.Rule
import uk.tojourn.twitter.stream.skybet.twitter.Tweet
import uk.tojourn.twitter.stream.skybet.twitter.TwitterResponse

class StringSpecExample : StringSpec() {
    private val objectMapper = jacksonObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
    private val tweet = Tweet(
        id = 1395753812430888961L,
        text = "RT @alyssa_merc: If you would have told 21-year-old Alyssa that one day she'd review her favorite trilogy of all time for one of the bigges…"
    )
    private val matchingRules = listOf(Rule(id = 1393191432836485124L, tag = "(mass effect)"))

    init {
        "Tweet Response Maps to Data Class" {
            val data =
                """{"data":{"id":"1395753812430888961","text":"RT @alyssa_merc: If you would have told 21-year-old Alyssa that one day she'd review her favorite trilogy of all time for one of the bigges…"},"matching_rules":[{"id":1393191432836485124,"tag":"(mass effect)"}]}"""
            val twitterResponse = objectMapper.readValue(data, TwitterResponse::class.java)
            twitterResponse.asClue {
                it.data shouldBe tweet
                it.matchingRules shouldBe matchingRules
            }
        }
    }
}


