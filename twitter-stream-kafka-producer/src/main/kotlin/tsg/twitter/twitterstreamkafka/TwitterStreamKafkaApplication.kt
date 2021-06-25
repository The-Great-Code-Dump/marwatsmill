package tsg.twitter.twitterstreamkafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.boot.runApplication
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import java.io.Serializable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.kafka.annotation.EnableKafka
import tsg.twitter.twitterstreamkafka.config.KafkaConfig
import tsg.twitter.twitterstreamkafka.config.TwitterConfig

@SpringBootApplication
@ConfigurationPropertiesScan("tsg.twitter.twitterstreamkafka.config")
class TwitterStreamKafkaApplication

fun main(args: Array<String>) {
    runApplication<TwitterStreamKafkaApplication>(*args)
}

@Configuration
@EnableKafka
class TwitterStreamKafkaApplicationConfig(val twitterConfig: TwitterConfig, val props: KafkaConfig) {

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

    @Bean
    fun producerConfigs(): Map<String, Serializable?> {
        return mapOf(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to props.server,
            KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
        )
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(producerConfigs())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        return KafkaTemplate(producerFactory())
    }
}
