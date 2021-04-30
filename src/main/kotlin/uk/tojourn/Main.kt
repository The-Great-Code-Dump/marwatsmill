@file:Suppress("EXPERIMENTAL_IS_NOT_ENABLED")

package uk.tojourn

import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import uk.tojourn.modules.consumerModule
import uk.tojourn.twitter.TwitterConsumer

@KoinApiExtension
class Application : KoinComponent {
    private val twitterConsumer by inject<TwitterConsumer>()
    fun letsGo()= twitterConsumer.whatsMyToken()
}


@OptIn(KoinApiExtension::class)
fun main() {
    startKoin{
        modules(consumerModule)
    }
    print(Application().letsGo())
   // SimpleProducer("localhost:9092").produce(2)
}