package uk.tojourn.twitterstreamkafka.twitter

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class TwitterReactiveClient(private val config: TwitterConfig) {

    private val webClient =
        WebClient.builder()
            .baseUrl(config.url)
            .defaultHeader("Authorization", "Bearer ${config.bearerToken}")
            .defaultHeader(
                "Cookie",
                "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609"
            )
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-type", "application/json")
            .build()
    
    fun getTweets() = webClient.get().uri(config.url).retrieve().bodyToFlux(String::class.java)
}