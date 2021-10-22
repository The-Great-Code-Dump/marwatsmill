package uk.tojourn.twitter.stream.skybet

import TwitterConfig
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient


@SpringBootApplication
@EnableConfigurationProperties(TwitterConfig::class)
class TwitterStreamApplication

fun main(args: Array<String>) {
    runApplication<TwitterStreamApplication>(*args)
}

@Configuration
class TwitterStreamApplicationConfig(val twitterConfig: TwitterConfig) {

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper =
        jacksonObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

    @Bean
    fun webClient(): WebClient =
        WebClient.builder()
            .baseUrl(twitterConfig.url)
            .defaultHeader("Authorization", "Bearer ${twitterConfig.bearerToken}")
            .defaultHeader(
                "Cookie",
                "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609"
            )
            .defaultHeader("Content-type", "application/json")
            .build()


}
