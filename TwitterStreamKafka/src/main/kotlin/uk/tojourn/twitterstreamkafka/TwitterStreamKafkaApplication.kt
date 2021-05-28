package uk.tojourn.twitterstreamkafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient
import uk.tojourn.twitterstreamkafka.twitter.TwitterConfig

@SpringBootApplication
@EnableConfigurationProperties(value = [TwitterConfig::class])
class TwitterStreamKafkaApplication

fun main(args: Array<String>) {
    runApplication<TwitterStreamKafkaApplication>(*args)
}

@Configuration
class TwitterStreamKafkaApplicationConfig(val config: TwitterConfig) {
    @Primary
    @Bean
    fun objectMapper(): ObjectMapper =
            jacksonObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

    @Bean
    fun webClient(): WebClient =
            WebClient.builder()
                    .baseUrl(config.url)
                    .defaultHeader("Authorization", "Bearer ${config.bearerToken}")

                    .defaultHeader(
                            "Cookie",
                            "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609"
                    )
                    .defaultHeader("Content-type", "application/json")
                    .build()
}
