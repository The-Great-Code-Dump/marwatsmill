package uk.tojourn.twitterstreamkafka.twitter

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import uk.tojourn.twitterstreamkafka.config.TwitterConfig

private val logger = KotlinLogging.logger { }

@Component
class TwitterReactiveRepository(private val objectMapper: ObjectMapper, private val config: TwitterConfig, private val webClient: WebClient) {

    fun getTweets(): Flux<TwitterResponse> = webClient
        .get()
        .uri(config.url)
        .accept(MediaType.APPLICATION_OCTET_STREAM)
        .retrieve()
        .bodyToFlux(String::class.java)
        .filterWhen { Mono.just(!it.isNullOrBlank()) }
        .doOnNext { logger.debug { "Raw Response: $it" } }
        .flatMap {
            Mono.fromCallable {
                objectMapper.readValue(it, TwitterResponse::class.java)
            }
                .flux()
                .onErrorResume { error ->
                    logger.error(error) { "An Error occured: $error" }
                    Flux.empty()
                }
        }
}
