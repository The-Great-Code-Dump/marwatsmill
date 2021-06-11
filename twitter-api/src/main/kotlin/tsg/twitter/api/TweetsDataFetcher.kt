package tsg.twitter.api

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class TweetsDataFetcher(private val tweetsRepository: TweetsRepository) {
    @DgsQuery
    fun tweetsByAuthorId(@InputArgument authorId: String) = tweetsRepository.getTweetByAuthor(authorId)

    @DgsQuery
    fun tweet(@InputArgument id: String) = tweetsRepository.getTweet(id)
}