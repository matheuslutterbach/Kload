# Kload

A lightweight, Kotlin-based load testing framework built for performance and simplicity.

## Overview

Kload is a modern load testing tool written in Kotlin that allows you to test the performance and reliability of your HTTP services under various load conditions. It provides real-time metrics collection and analysis capabilities to help you understand your application's behavior under stress.

## Features

- **Asynchronous Load Testing**: Built with Kotlin coroutines for efficient concurrent testing
- **Real-time Metrics**: Comprehensive metrics gathering using Micrometer with latency tracking
- **HTTP Client Support**: Integrated with Ktor client for robust HTTP-based load testing
- **DSL Configuration**: Intuitive domain-specific language for test configuration
- **Concurrent Users**: Support for simulating multiple concurrent users
- **Flexible Scenarios**: Define custom request scenarios with different paths and weights
- **Built-in Reporting**: Automatic summary generation with request count and mean latency

## Technologies Used

- **Kotlin**: 2.1.21
- **Ktor Client**: 2.3.13 (HTTP client)
- **Kotlinx Coroutines**: 1.9.0 (Asynchronous programming)
- **Kotlinx Serialization**: 1.7.1 (JSON handling)
- **Micrometer**: 1.13.3 (Metrics collection)
- **JUnit Jupiter**: 5.11.0 (Testing framework)
- **Gradle**: Build automation

## Prerequisites

- Java 24 or higher
- Kotlin 2.1.21 or higher

## Getting Started

### 1. Create a Simple Load Test

```kotlin
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    val test = loadTest {
        target("https://httpbin.org/")
        users(3)
        duration(5.seconds)
        scenario {
            get("/get")
        }
    }
    test.run()
}
```

### 2. Multiple Scenarios

```kotlin
val test = loadTest {
    target("https://jsonplaceholder.typicode.com/")
    users(10)
    duration(30.seconds)
    
    scenario {
        get("/posts")
    }
    
    scenario {
        get("/users")
    }
    
    scenario {
        get("/comments")
    }
}
```

### 3. Running and Analyzing Results

After running a test, Kload provides:
- Real-time request status updates
- Response times for individual requests
- Summary statistics including total requests and mean latency

## Installation

### Clone the repository
```bash
git clone https://github.com/matheuslutterbach/Kload.git
cd Kload
```

### Build the project
```bash
./gradlew build
```

## Usage

### Basic Load Test

```kotlin
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    val test = loadTest {
        target("https://httpbin.org/")
        users(5)
        duration(10.seconds)
        scenario {
            get("/get")
        }
    }
    test.run()
}
```

### Advanced Configuration

```kotlin
val test = loadTest {
    // Set the target URL
    target("https://api.example.com/")
    
    // Number of concurrent users
    users(10)
    
    // Test duration
    duration(30.seconds)
    
    // Define test scenarios
    scenario {
        get("/users")
    }
    
    scenario {
        get("/posts")
    }
}

// Run the test
test.run()
```

### Example Output

```
Running load test on https://httpbin.org/ with 5 users for 5s
Response: 200 OK, Duration: 245ms
Response: 200 OK, Duration: 189ms
Response: 200 OK, Duration: 198ms
...
Total requests: 127, Mean latency: 201.5ms
```

### Running Tests

```bash
./gradlew test
```

### Build and Run

```bash
./gradlew run
```

## Project Structure

```
Kload/
├── src/
│   ├── main/
│   │   └── kotlin/
│   │       ├── LoadTest.kt
│   │       └── Metrics.kt
│   └── test/
│       └── kotlin/
│           └── LoadTestTest.kt
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## API Reference

### LoadTest Configuration

- **`target(url: String)`**: Sets the base URL for the load test
- **`users(count: Int)`**: Sets the number of concurrent users (default: 1)
- **`duration(time: Duration)`**: Sets the test duration (default: 10 seconds)
- **`scenario(block: Scenario.() -> Unit)`**: Defines a test scenario

### Scenario Configuration

- **`get(path: String)`**: Defines a GET request to the specified path
- **`weight: Double`**: Sets the scenario weight (default: 1.0)

### Metrics

The framework automatically collects and reports:
- **Total requests**: Number of requests executed
- **Mean latency**: Average response time in milliseconds
- **Real-time feedback**: Individual request status and duration

## Configuration

The project uses Gradle for build configuration. Key dependencies include:

- Ktor for HTTP client operations
- Kotlinx Coroutines for async operations
- Micrometer for metrics collection
- JUnit for testing

## Development

### Building from Source

1. Ensure you have Java 24 installed
2. Clone the repository
3. Run `./gradlew build`

### Running Tests

The project includes comprehensive tests:

```bash
./gradlew test
```

### Example Test

```kotlin
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
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

**Matheus Lutterbach**
- GitHub: [@matheuslutterbach](https://github.com/matheuslutterbach)

## Support

If you encounter any issues or have questions, please open an issue on the GitHub repository.
