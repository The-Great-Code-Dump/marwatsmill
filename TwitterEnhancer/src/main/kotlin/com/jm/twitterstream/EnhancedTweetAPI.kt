package com.jm.twitterstream

import com.fasterxml.jackson.databind.JsonNode
import com.jm.twitterstream.dto.EnhancedTweet
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import com.fasterxml.jackson.annotation.JsonProperty


@RestController
class EnhancedTweetAPI(val twitterService: TwitterService) {

    @GetMapping(value = ["/"])
    fun getConfiguration(): TwitterStreamConfiguration {
        return twitterService.twitterStreamConfiguration
    }

    @GetMapping(value = ["/twittered/rate"])
    fun getRateLimit(): JsonNode? = twitterService.getTwitterClient().rateLimitStatus.resources["tweets"]


    @GetMapping(value = ["/twittered/{tweetId}"])
    fun testTwittered(@PathVariable("tweetId") tweetId: String): EnhancedTweet =
            twitterService.getEnhancedTweet(tweetId)

    @GetMapping(value = ["/twittered-string/{tweetId}"])
    fun testTwitteredAsString(@PathVariable("tweetId") tweetId: String): String =
            twitterService.getEnhancedTweetAsString(tweetId)
}