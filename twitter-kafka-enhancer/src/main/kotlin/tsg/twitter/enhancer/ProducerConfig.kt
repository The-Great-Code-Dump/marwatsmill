package tsg.twitter.enhancer

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.ProducerFactory
import org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.kafka.support.serializer.JsonSerializer
import java.io.Serializable
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import tsg.twitter.enhancer.dto.EnhancedTweet

@Configuration
class ProducerConfig(val props: KafkaConfig) {

    @Bean
    fun producerConfigs(): Map<String, Serializable?> {
        return mapOf(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to props.server,
                KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, EnhancedTweet> {
        return DefaultKafkaProducerFactory(producerConfigs())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, EnhancedTweet> {
        return KafkaTemplate(producerFactory())
    }
}