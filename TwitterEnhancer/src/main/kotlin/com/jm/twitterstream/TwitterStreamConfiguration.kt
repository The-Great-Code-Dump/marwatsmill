package com.jm.twitterstream

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "twitter")
data class TwitterStreamConfiguration(
        val apiKey: String? = null,
        val apiSecretKey: String? = null,
        val accessToken: String? = null,
        val accessTokenSecret: String? = null
)

fun TwitterStreamConfiguration.isNotValidConfiguraion(): Boolean {
        val credentials = listOfNotNull(
                this.apiKey,
                this.apiSecretKey,
                this.accessToken,
                this.accessTokenSecret
        )

        return credentials.isNotEmpty() && credentials.any { it == "" }
}