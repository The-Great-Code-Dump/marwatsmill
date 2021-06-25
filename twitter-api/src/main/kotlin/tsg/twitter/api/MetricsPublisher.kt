package tsg.twitter.api

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import org.springframework.stereotype.Component

@Component
class MetricsPublisher(private val meterRegistry: MeterRegistry) {

    fun totalRecordsFailedToProcess() {
        Counter.builder("test")
            .register(meterRegistry)
            .increment()
    }
}