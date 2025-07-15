import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class LoadTestTest {

    @Test
    fun testLoadTest() = runBlocking {
        val test = loadTest {
            target("https://httpbin.org/")
            users(5)
            duration(5.seconds)
            scenario {
                get("/get")
            }
        }
        test.run()
    }
}