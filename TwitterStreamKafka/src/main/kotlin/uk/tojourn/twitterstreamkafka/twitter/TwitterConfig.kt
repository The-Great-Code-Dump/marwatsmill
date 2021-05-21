package uk.tojourn.twitterstreamkafka.twitter

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "twitter")
@ConstructorBinding
data class TwitterConfig(
    val bearerToken: String? = null,
    val url: String = "https://api.twitter.com/2/tweets/search/stream",
    val rules: String? = ""
)