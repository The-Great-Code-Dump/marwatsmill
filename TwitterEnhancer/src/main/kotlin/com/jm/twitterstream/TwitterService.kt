package com.jm.twitterstream

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.redouane59.twitter.TwitterClient
import com.github.redouane59.twitter.signature.TwitterCredentials
import com.jm.twitterstream.dto.EnhancedTweet
import org.springframework.stereotype.Service

@Service
class TwitterService(val twitterStreamConfiguration: TwitterStreamConfiguration, val mapper: ObjectMapper) {

    fun getTwitterClient(): TwitterClient {
        val twitterCredentials = TwitterCredentials.builder()
                .accessToken(twitterStreamConfiguration.accessToken)
                .accessTokenSecret(twitterStreamConfiguration.accessTokenSecret)
                .apiKey(twitterStreamConfiguration.apiKey)
                .apiSecretKey(twitterStreamConfiguration.apiSecretKey)
                .build()

        return TwitterClient(twitterCredentials)
    }

    fun getEnhancedTweet(tweetId: String): EnhancedTweet {
        val twitterClient = getTwitterClient()

        val response = twitterClient.requestHelperV2.getRequest(twitterClient.urlHelper.getTweetUrl(tweetId), String::class.java)

        return mapper.readValue(response.get())
    }
}