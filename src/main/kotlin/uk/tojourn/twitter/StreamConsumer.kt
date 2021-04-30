package uk.tojourn.twitter
import com.github.kittinunf.fuel.core.Headers.Companion.AUTHORIZATION
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost


 class TwitterConsumer(private val token: String)  {
     fun whatsMyToken() = token

    fun setRule(rule :String){
//        "https://api.twitter.com/2/tweets/search/stream/rules".httpPost().header(AUTHORIZATION, "bearer: $token")
//            .body("my body is plain")
//        )
    }
}