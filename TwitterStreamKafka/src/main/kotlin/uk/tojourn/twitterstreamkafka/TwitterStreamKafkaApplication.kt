package uk.tojourn.twitterstreamkafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import uk.tojourn.twitterstreamkafka.twitter.TwitterConfig

@SpringBootApplication
@EnableConfigurationProperties(value = [TwitterConfig::class])
class TwitterStreamKafkaApplication

fun main(args: Array<String>) {
	runApplication<TwitterStreamKafkaApplication>(*args)

}
