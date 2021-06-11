package tsg.twitter.twitterstreamkafka.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "kafka")
@ConstructorBinding
data class KafkaConfig(
        val topics: Topic,
        val server: String? = "localhost:1234"
)

data class Topic(val twitter: String)