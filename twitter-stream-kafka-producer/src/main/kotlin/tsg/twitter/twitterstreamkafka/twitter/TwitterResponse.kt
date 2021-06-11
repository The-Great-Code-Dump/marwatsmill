package tsg.twitter.twitterstreamkafka.twitter

data class TwitterResponse(val data: Tweet, val matchingRules: List<Rule> )
data class Tweet(val id: Long, val text: String)
data class Rule(val id: Long, val tag: String)