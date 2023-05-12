package gopas


fun <T, R> Sequence<T>.myMap(tr: (T) -> R): Sequence<R> =
    object : Sequence<R> {
        override fun iterator(): Iterator<R> =
            object : Iterator<R> {
                val iit = this@myMap.iterator()
                override fun hasNext() = iit.hasNext()
                override fun next() = tr(iit.next())
            }

    }

fun <T> Sequence<T>.myfilter(pr: (T) -> Boolean): Sequence<T> =
    object : Sequence<T> {
        val iit = this@myfilter.iterator()
        override fun iterator(): Iterator<T> =
            object : Iterator<T> {
                var x: T? = null
                override fun hasNext(): Boolean {
                    if (x != null) return true
                    while (iit.hasNext()) {
                        val nxt = iit.next()
                        if (pr(nxt)) {
                            x = nxt
                            return true
                        }
                    }
                    return false
                }

                override fun next(): T {
                    hasNext()
                    val r = x
                    x = null
                    return r ?: throw NoSuchElementException()
                }
            }
    }


fun main(args: Array<String>) {
    val i = sequenceOf(1, 2, 3, 4, 5, 6)
        .myfilter { it % 2 == 0 }.iterator()
    println(i.hasNext())
    println(i.hasNext())
    println(i.next())
    println(i.next())
    println(i.hasNext())
    println(i.next())
    println(i.hasNext())
    println(i.next())
}