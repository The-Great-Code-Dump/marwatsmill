package uk.tojourn.twitterstreamkafka.twitter

import org.springframework.context.annotation.Bean
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import java.net.http.HttpClient

class TwitterClient(private val config: TwitterConfig) {

    @Bean
    // We have to use spring's builder in order to expose http metrics
    private fun webClient(webClientBuilder: WebClient.Builder) =
        webClientBuilder
            .clientConnector(ReactorClientHttpConnector(httpClient()))
            .baseUrl(config.url)
            .build()

    @Bean
    fun httpClient() = HttpClient.create()
//        .secure { it.sslContext(sslContext()) }
//        .compress(true)
//        .tcpConfiguration { client ->
//            client
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfiguration.connectionTimeoutMillis.toInt())
//                .doOnConnected { conn ->
//                    conn.addHandlerLast(
//                        ReadTimeoutHandler(clientConfiguration.readTimeoutMillis, TimeUnit.MILLISECONDS)
//                    )
//                }
//        }
}