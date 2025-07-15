import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import java.util.concurrent.TimeUnit

class LoadTestMetrics(registry: MeterRegistry) {
    private val requestTimer: Timer = Timer.builder("loadtest.requests")
        .description("Time taken for requests")
        .register(registry)

    fun recordRequest(duration: Long) {
        requestTimer.record(duration, TimeUnit.MILLISECONDS)
    }

    fun summary(): String {
        return "Total requests: ${requestTimer.count()}, Mean latency: ${requestTimer.mean(TimeUnit.MILLISECONDS)}ms"
    }
}