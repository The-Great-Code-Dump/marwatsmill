package uk.tojourn.twitterstreamkafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import uk.tojourn.twitterstreamkafka.twitter.TwitterConfig

@SpringBootApplication
@EnableConfigurationProperties(value = [TwitterConfig::class])
class TwitterStreamKafkaApplication {
    @Primary
    @Bean
    fun objectMapper(): ObjectMapper =
        jacksonObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)


}

fun main(args: Array<String>) {
    runApplication<TwitterStreamKafkaApplication>(*args)
}