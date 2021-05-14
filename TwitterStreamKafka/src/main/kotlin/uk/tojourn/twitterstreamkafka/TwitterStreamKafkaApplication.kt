package uk.tojourn.twitterstreamkafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TwitterStreamKafkaApplication

fun main(args: Array<String>) {
	runApplication<TwitterStreamKafkaApplication>(*args)

}
