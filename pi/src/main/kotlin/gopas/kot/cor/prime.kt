package gopas.kot.cor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking


fun CoroutineScope.nat(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE) =
    produce {
        for (i in min..max)
            send(i)
    }

fun <T, R> ReceiveChannel<T>.map(cs: CoroutineScope, transf: (T) -> R) =
    cs.produce {
        for (i in this@map)
            send(transf(i))
    }

fun <T> ReceiveChannel<T>.filter(cs: CoroutineScope, pred: (T) -> Boolean) =
    cs.produce {
        for (i: T in this@filter)
            if (pred(i))
                send(i)
    }

fun CoroutineScope.prime(max: Int = Int.MAX_VALUE): ReceiveChannel<Int> =
    produce {
        var inpch = nat(min = 2)
        do {
            val p = inpch.receive()
            if (p <= max)
                send(p)
            else close()
            inpch = inpch.filter(this) { it % p != 0 }
        } while (p <= max)
    }

fun main() {
    runBlocking {
        prime().consumeEach {
            println(it)
        }
        println("*".repeat(20))
/*        nat(1, 10)
            .filter(this) { it % 2 == 0 }
            .map(this) { it * it }
            .consumeEach {
                println(it)
            }*/
    }
}