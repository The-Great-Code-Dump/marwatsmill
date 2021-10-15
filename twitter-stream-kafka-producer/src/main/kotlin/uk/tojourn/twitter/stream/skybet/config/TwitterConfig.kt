

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "twitter")
@ConstructorBinding
data class TwitterConfig(
    val bearerToken: String? = null,
    val url: String = "https://api.twitter.com/2/tweets/search/stream",
    val rules: Map<String, String>
)