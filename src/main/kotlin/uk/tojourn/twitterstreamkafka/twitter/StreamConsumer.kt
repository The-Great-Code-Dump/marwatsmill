package uk.tojourn.twitterstreamkafka.twitter


import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.springframework.stereotype.Component
import uk.tojourn.twitterstreamkafka.config.TwitterConfig
import javax.annotation.PostConstruct

@Component
class StreamConsumer(private val twitterConfig: TwitterConfig) {

    @PostConstruct
    fun setUpRules(){
        val client = HttpClients.createDefault()
        val httpPost = HttpPost(twitterConfig.url + "/rules")

        httpPost.entity = StringEntity(
            """{
                       "add": [{"value": "(mass effect) lang:en", "tag": "(mass effect)"}]
                  }""")
        httpPost.addHeader("Authorization", "Bearer ${twitterConfig.bearerToken}")
        httpPost.addHeader( "Cookie", "personalization_id=\"v1_EDnQr/y/NKEYHS9i1Z3jvA==\"; guest_id=v1%3A161978895237305609")
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        val response = client.execute(httpPost)
        if (response.statusLine.statusCode !in 200..299){
            throw Error("Its gone pete tong")
        }
        client.close()
    }
}