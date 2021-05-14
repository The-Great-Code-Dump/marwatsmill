package com.jm.twitterstream

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.boot.context.event.ApplicationStartingEvent
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import mu.KotlinLogging

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
): ApplicationListener<ApplicationStartedEvent> {

    override fun onApplicationEvent(event: ApplicationStartedEvent) {
        logger.info { "Attempting to validate Twitter configuration." }
        if(applicationContext.isRunning) {
            if (twitterStreamConfiguration.isNotValidConfiguraion()) {
                logger.error { "Twitter configuration invalid, please check required ENV vars." }
                applicationContext.close()
            }
        }
    }
}

@RestController
@Configuration
class Api(val twitterStreamConfiguration: TwitterStreamConfiguration) {

    @GetMapping(value = ["/"])
    fun getConfiguration(): TwitterStreamConfiguration {
        return twitterStreamConfiguration
    }
}