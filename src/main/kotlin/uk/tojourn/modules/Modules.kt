package uk.tojourn.modules

import org.koin.dsl.module
import uk.tojourn.twitter.TwitterConsumer

val consumerModule = module{
    single { getBearerTokenFromEnvironment()}
    single { TwitterConsumer(get()) }
}

private fun getBearerTokenFromEnvironment() : String {
    val token = System.getenv("TWITTER_BEARER_TOKEN")
    if (token.isNullOrBlank()){
        throw Error("Missing env var TWITTER_BEARER_TOKEN, You can't start with out a twitter token mate! https://developer.twitter.com/en/docs/authentication/oauth-2-0/bearer-tokens#:~:text=Login%20to%20your%20Twitter%20account,Generate%22%20next%20to%20Bearer%20Token. ")
    }
    return token
}