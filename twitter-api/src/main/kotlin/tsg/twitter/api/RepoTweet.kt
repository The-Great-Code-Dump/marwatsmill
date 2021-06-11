package tsg.twitter.api

data class RepoTweet (
    val id: Int,
    val authorId: Int,
    val conversationId: Int,
    val createdAt: String?,
    val lang: String?,
    val possibleSensitive: Boolean,
    val replySettings: String?,
    val source: String?,
    val text: String?
)
