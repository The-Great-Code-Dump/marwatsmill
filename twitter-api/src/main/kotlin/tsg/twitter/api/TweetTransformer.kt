package tsg.twitter.api

import org.springframework.stereotype.Component

@Component
class TweetTransformer : (RepoTweet) -> Tweet {
    override fun invoke(repoTweet: RepoTweet) =
        with(repoTweet) {
            Tweet(
                id = id,
                authorId = authorId,
                conversationId = conversationId,
                createdAt = createdAt,
                lang = lang,
                possibleSensitive = possibleSensitive,
                replySettings = replySettings,
                source = source,
                text = text
            )
        }


}