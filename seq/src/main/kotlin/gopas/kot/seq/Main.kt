package gopas.kot.seq


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
        override fun iterator(): Iterator<T> =
            object : Iterator<T> {
                val iit = this@myfilter.iterator()
                override fun hasNext() {

                }

                override fun next() {

                }
            }

    }


fun main(args: Array<String>) {
    sequenceOf(1, 2, 3)
        .myMap { it * it }
        .myMap { it * it }
        .forEach {
            println(it)
        }
}