package tsg.twitter.enhancer.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class  EnhancedTweet(
        @JsonProperty("data")
    val data: Data?,
        @JsonProperty("includes")
    val includes: Includes?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Data(
        @JsonProperty("attachments")
    val attachments: Attachments?,
        @JsonProperty("author_id")
    val authorId: String?,
        @JsonProperty("context_annotations")
    val contextAnnotations: List<ContextAnnotation>?,
        @JsonProperty("conversation_id")
    val conversationId: String?,
        @JsonProperty("created_at")
    val createdAt: String?,
        @JsonProperty("in_reply_to_user_id")
    val inReplyToUserId: String?,
        @JsonProperty("entities")
    val entities: Entities?,
        @JsonProperty("id")
    val id: String?,
        @JsonProperty("lang")
    val lang: String?,
        @JsonProperty("possibly_sensitive")
    val possiblySensitive: Boolean?,
        @JsonProperty("public_metrics")
    val publicMetrics: PublicMetrics?,
        @JsonProperty(value = "referenced_tweets", required = false)
    val referencedTweets: List<ReferencedTweet>?,
        @JsonProperty("reply_settings")
    val replySettings: String?,
        @JsonProperty("source")
    val source: String?,
        @JsonProperty("text")
    val text: String?
)

data class Includes(
        @JsonProperty("tweets")
        val tweets: List<Tweet>?,
        @JsonProperty("users")
        val users: List<User>?
)

data class Tweet(
        @JsonProperty("author_id")
    val authorId: String?,
        @JsonProperty("conversation_id")
    val conversationId: String?,
        @JsonProperty("context_annotations")
    val contextAnnotations: List<ContextAnnotation>?,
        @JsonProperty("created_at")
    val createdAt: String?,
        @JsonProperty("entities")
    val entities: Entities?,
        @JsonProperty("id")
    val id: String?,
        @JsonProperty("lang")
    val lang: String?,
        @JsonProperty("in_reply_to_user_id")
    val inReplyToUserId: String?,
        @JsonProperty("possibly_sensitive")
    val possiblySensitive: Boolean?,
        @JsonProperty("public_metrics")
    val publicMetrics: PublicMetrics?,
        @JsonProperty("geo")
    val geo: Geo?,
        @JsonProperty("reply_settings")
    val replySettings: String?,
        @JsonProperty("source")
    val source: String?,
        @JsonProperty("text")
    val text: String?,
        @JsonProperty("attachments")
    val attachments: Attachments?,
        @JsonProperty(value = "referenced_tweets", required = false)
    val referencedTweets: List<ReferencedTweet>?,
)

data class Geo(
    @JsonProperty("place_id")
    val placeId: String?
)

data class Attachments(
    @JsonProperty("media_keys")
    val mediaKeys: List<String>?,
    @JsonProperty("poll_ids")
    val pollIds: List<Long>?
)

data class ContextAnnotation(
        @JsonProperty("domain")
    val domain: Domain?,
        @JsonProperty("entity")
    val entity: Entity?
)

data class Entities(
        @JsonProperty("annotations")
    val annotations: List<Annotation>?,
        @JsonProperty("cashtags")
    val cashtags: List<Cashtag>?,
        @JsonProperty("hashtags")
    val hashtags: List<Hashtag>?,
        @JsonProperty("mentions")
    val mentions: List<Mention>?,
        @JsonProperty("urls")
    val urls: List<Url>?
)

data class Cashtag(
        @JsonProperty("end")
        val end: Int?,
        @JsonProperty("start")
        val start: Int?,
        @JsonProperty("tag")
        val tag: String?
)

data class PublicMetrics(
    @JsonProperty("like_count", required = false)
    val likeCount: Int?,
    @JsonProperty("quote_count", required = false)
    val quoteCount: Int?,
    @JsonProperty("reply_count", required = false)
    val replyCount: Int?,
    @JsonProperty("retweet_count", required = false)
    val retweetCount: Int?
)

data class Domain(
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("name")
    val name: String?
)

data class Entity(
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("name")
    val name: String?
)

data class Annotation(
    @JsonProperty("end")
    val end: Int?,
    @JsonProperty("normalized_text")
    val normalizedText: String?,
    @JsonProperty("probability")
    val probability: Double?,
    @JsonProperty("start")
    val start: Int?,
    @JsonProperty("type")
    val type: String?
)

data class Mention(
    @JsonProperty("id")
    val id: Long?,
    @JsonProperty("end")
    val end: Int?,
    @JsonProperty("start")
    val start: Int?,
    @JsonProperty("username")
    val username: String?
)

data class Url(
        @JsonProperty("description")
        val description: String?,
        @JsonProperty("display_url")
        val displayUrl: String?,
        @JsonProperty("end")
        val end: Int?,
        @JsonProperty("expanded_url")
        val expandedUrl: String?,
        @JsonProperty("images")
        val images: List<Image>?,
        @JsonProperty("start")
        val start: Int?,
        @JsonProperty("status")
        val status: Int?,
        @JsonProperty("title")
        val title: String?,
        @JsonProperty("unwound_url")
        val unwoundUrl: String?,
        @JsonProperty("url")
        val url: String?
)

data class Image(
        @JsonProperty("height")
        val height: Int?,
        @JsonProperty("url")
        val url: String?,
        @JsonProperty("width")
        val width: Int?
)

data class User(
        @JsonProperty("created_at")
    val createdAt: String?,
        @JsonProperty("description")
    val description: String?,
        @JsonProperty("id")
    val id: String?,
        @JsonProperty("location")
    val location: String?,
        @JsonProperty("name")
    val name: String?,
        @JsonProperty("pinned_tweet_id")
    val pinnedTweetId: String?,
        @JsonProperty("profile_image_url")
    val profileImageUrl: String?,
        @JsonProperty("protected")
    val protected: Boolean?,
        @JsonProperty("public_metrics")
    val publicMetrics: PublicMetricsX?,
        @JsonProperty("url")
    val url: String?,
        @JsonProperty("username")
    val username: String?,
        @JsonProperty("verified")
    val verified: Boolean?
)

data class PublicMetricsX(
    @JsonProperty("followers_count")
    val followersCount: Int?,
    @JsonProperty("following_count")
    val followingCount: Int?,
    @JsonProperty("listed_count")
    val listedCount: Int?,
    @JsonProperty("tweet_count")
    val tweetCount: Int?
)

data class Hashtag(
    @JsonProperty("end")
    val end: Int?,
    @JsonProperty("start")
    val start: Int?,
    @JsonProperty("tag")
    val tag: String?
)

data class ReferencedTweet(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("type")
    val type: String?
)