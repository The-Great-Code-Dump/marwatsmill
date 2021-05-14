package uk.tojourn.twitterstreamkafka

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "twitter")
@ConstructorBinding
class RulesProperties {
    var rules: String = ""
}