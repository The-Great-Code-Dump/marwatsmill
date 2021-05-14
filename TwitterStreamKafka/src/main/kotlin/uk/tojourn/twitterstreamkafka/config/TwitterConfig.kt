package uk.tojourn.twitterstreamkafka.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "twitter")
@EnableAsync
@EnableConfigurationProperties
data class TwitterConfig(
    var bearerToken: String? = null,
    val url: String = "https://api.twitter.com/2/tweets/search/stream"
)