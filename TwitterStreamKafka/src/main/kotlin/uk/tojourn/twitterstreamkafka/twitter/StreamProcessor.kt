package uk.tojourn.twitterstreamkafka.twitter

import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.io.BufferedReader

sealed class Tweet

data class PopulatedTweet(val tweet: String): Tweet()
object NoTweet : Tweet()

fun BufferedReader.getNextTweet(): Tweet {
    val potentialTweet = this.readLine()
    if(potentialTweet != null) {
        return PopulatedTweet(tweet = potentialTweet)
    }

    return NoTweet
}

private val logger = KotlinLogging.logger { }

@Component
class StreamProcessor(val twitterRepo: TwitterReactiveClient) {
    fun processTweets() {
        twitterRepo
            .getTweets()
            .doOnEach { logger.info { it } }
            .subscribe()
    }
}

@Controller
class StartyBoy(val processor: StreamProcessor) {
    
    @GetMapping("/")
    fun start() : String{
        processor.processTweets()
        return "Yay"
    }
}