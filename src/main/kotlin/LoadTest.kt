import io.ktor.client.*
import io.ktor.client.request.*
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import kotlinx.coroutines.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class LoadTest {
    var targetUrl: String = ""
    var users: Int = 1
    var duration: Duration = 10.seconds
    private val scenarios = mutableListOf<Scenario>()
    private val registry = SimpleMeterRegistry()
    private val metrics = LoadTestMetrics(registry)

    fun target(url: String) {
        targetUrl = url
    }

    fun users(count: Int) {
        users = count
    }

    fun duration(time: Duration) {
        duration = time
    }

    fun scenario(block: Scenario.() -> Unit) {
        scenarios.add(Scenario().apply(block))
    }

    suspend fun run() {
        println("Running load test on $targetUrl with $users users for $duration")
        val client = HttpClient()
        val jobs = List(users) {
            CoroutineScope(Dispatchers.IO).launch {
                while (isActive) {
                    scenarios.forEach { scenario ->
                        val start = System.currentTimeMillis()
                        val response = client.get("${targetUrl}${scenario.path}")
                        val duration = System.currentTimeMillis() - start
                        metrics.recordRequest(duration)
                        println("Response: ${response.status}, Duration: ${duration}ms")
                    }
                }
            }
        }
        delay(duration)
        jobs.forEach { it.cancel() }
        client.close()
        println(metrics.summary())
    }

}


class Scenario {
    var path: String = ""
    var weight: Double = 1.0

    fun get(path: String, block: Scenario.() -> Unit = {}) {
        this.path = path
        apply(block)
    }
}

fun loadTest(block: LoadTest.() -> Unit): LoadTest {
    return LoadTest().apply(block)
}