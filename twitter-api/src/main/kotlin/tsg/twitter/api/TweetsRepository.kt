package tsg.twitter.api

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class TweetsRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcOperations,
    private val tweetTransformer: TweetTransformer
) {

    fun getTweet(id: String): Tweet? =
        namedParameterJdbcTemplate.query(
            SELECT_TWEET_BY_ID,
            mapOf("id" to id.toInt())
        ) { rs, _ ->
            resultSetMapper(rs)
        }.firstOrNull()?.let(tweetTransformer)

    fun getTweetByAuthor(authorId: String): List<Tweet>? =
        namedParameterJdbcTemplate.query(
            SELECT_TWEET_BY_AUTHOR_ID,
            mapOf("authorId" to authorId.toInt())
        ) { rs, _ ->
            resultSetMapper(rs)
        }.map(tweetTransformer)

    fun resultSetMapper(resultSet: ResultSet) =
        RepoTweet(
            id = resultSet.getInt("id"),
            authorId = resultSet.getInt("author_id"),
            conversationId = resultSet.getInt("conversation_id"),
            createdAt = resultSet.getString("created_at"),
            lang = resultSet.getString("lang"),
            possibleSensitive = resultSet.getBoolean("possible_sensitive"),
            replySettings = resultSet.getString("reply_settings"),
            source = resultSet.getString("source"),
            text = resultSet.getString("text")
        )

    private companion object {
        private const val SELECT_TWEET_BY_ID = "select * from tweet where id = :id"

        private const val SELECT_TWEET_BY_AUTHOR_ID = "select * from tweet where author_id = :authorId"
    }
}