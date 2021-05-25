package com.jm.twitterstream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.redouane59.twitter.TwitterClient
import com.github.redouane59.twitter.signature.TwitterCredentials
import com.jm.twitterstream.dto.EnhancedTweet
import org.springframework.stereotype.Service

@Service
class TwitterService(val twitterStreamConfiguration: TwitterStreamConfiguration, val mapper: ObjectMapper) {

    private val twitterClient = TwitterClient(createTwitterCredentials())

    private fun createTwitterCredentials() =
        TwitterCredentials
            .builder()
            .accessToken(twitterStreamConfiguration.accessToken)
            .accessTokenSecret(twitterStreamConfiguration.accessTokenSecret)
            .apiKey(twitterStreamConfiguration.apiKey)
            .apiSecretKey(twitterStreamConfiguration.apiSecretKey)
            .build()

    fun getEnhancedTweetAsString(tweetId: String) = makeRequest(tweetId)

    private fun makeRequest(tweetId: String) =
        twitterClient.requestHelperV2.getRequest(twitterClient.urlHelper.getTweetUrl(tweetId), String::class.java).get()

    fun getEnhancedTweet(tweetId: String): EnhancedTweet = mapper.readValue(makeRequest(tweetId))

    fun getRateLimit() = twitterClient.rateLimitStatus.resources["tweets"]
}