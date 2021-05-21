package uk.tojourn.twitterstreamkafka.twitter

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger { }

@Component
class TwitterReactiveRepository(private val objectMapper: ObjectMapper, private val config: TwitterConfig) {

    private val webClient =
        WebClient.builder()
            .baseUrl(config.url)
            .defaultHeader("Authorization", "Bearer ${config.bearerToken}")

            .defaultHeader(
                "Cookie",
                "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609"
            )
            .defaultHeader("Content-type", "application/json")
            .build()

    @EventListener(classes = [ApplicationReadyEvent::class])
    @Order(0)
    fun setUpRules(): String? {
        logger.debug { "Setting rules... ${config.rules}" }
        val response = webClient.post()
            .uri(config.url + "/rules")
            .body(BodyInserters.fromValue("""{"add": [{"value": "(MassEffect) lang:en", "tag": "(mass effect)"} ,{"value": "(rather) lang:en", "tag": "(Lots of tweets)"}]}""".trimMargin()))
            .retrieve().bodyToMono(String::class.java).block()
        logger.debug { "Response: $response"}

        return response
    }

    fun getTweets(): Flux<TwitterResponse> = webClient
        .get()
        .uri(config.url)
        .accept(MediaType.APPLICATION_OCTET_STREAM)
        .retrieve()
        .bodyToFlux(String::class.java)
        .filterWhen { Mono.just(!it.isNullOrBlank()) }
        .doOnNext { logger.debug { "Raw Response: $it" } }
        .flatMap {
            Mono.fromCallable { objectMapper.readValue(it, TwitterResponse::class.java) }
            .flux()
            .onErrorResume { error ->
                logger.error(error) { "Your mams a cunt: $error" }
                Flux.empty()
            }
        }
}
