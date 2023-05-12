package gopas.kot.cor

import kotlinx.coroutines.*


fun CoroutineScope.pi(n: Long = 100_000_000, timeout: Long = Long.MAX_VALUE): Deferred<Double> =
    async {
        var pi = 0.0
        try {
            withTimeout(timeout) {
                for (i in 1..n step 4) {
                    pi += 1.0 / i - 1.0 / (i + 2)
                    yield()
                }
            }
        } catch (ex : TimeoutCancellationException) {
            println("timeout")
        }
        pi * 4
    }

fun main(args: Array<String>) {
    runBlocking {
          println(pi(timeout = 300).await())
    }
}

