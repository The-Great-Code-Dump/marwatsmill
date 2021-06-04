package com.jm.twitterstream

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@EnableConfigurationProperties(TwitterStreamConfiguration::class)
class TwitterStreamApplication

fun main(args: Array<String>) {
    runApplication<TwitterStreamApplication>(*args).registerShutdownHook()
}

@Configuration
class TwitterStreamContextConfiguration(
        val applicationContext: ConfigurableApplicationContext,
        val twitterStreamConfiguration: TwitterStreamConfiguration
) : ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        logger.info { "Attempting to validate Twitter configuration." }
        if (applicationContext.isRunning) {
            if (twitterStreamConfiguration.isNotValidConfiguraion()) {
                logger.error { "Twitter configuration invalid, please check required ENV vars." }
                applicationContext.close()
            }
        }
    }

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()

    @Bean(value = ["missingPropsObjectMapper"])
    fun missingPropsObjectMapper(): ObjectMapper =
        jacksonObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
}