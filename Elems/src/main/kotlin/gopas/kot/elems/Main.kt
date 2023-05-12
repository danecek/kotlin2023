package gopas.kot.elems

interface Elem {

    val content: List<String>
    val width: Int
        get() = if (content.isEmpty()) 0 else
            content.maxOf { it.length }//= content.maxOf({ s: String -> s.length })
    val height: Int
        get() = content.size
}

open class ToStringElem(override val content: List<String>) : Elem {
    override fun toString() = content.joinToString(separator = "\n")
}

infix fun Elem.above(e: Elem): Elem {
    check(width == e.width) { "widths differ" }
    return BasicElem(content + e.content) // (this.content.plus(e.content))
}

infix fun Elem.beside(e: Elem): Elem {
    check(height == e.height) { "heights differ" }
    val zipped: List<Pair<String, String>> = content zip e.content
    return BasicElem(zipped.map { it.first + it.second })
}

fun Elem.widenL(n: Int): Elem {
    check(n >= width)
    return this beside EmptyElem(n - width, height)
}

fun Elem.widenR(n: Int): Elem {
    check(n >= width)
    return EmptyElem(n - width, height) beside this
}

fun Elem.heightenU(h: Int): Elem {
    check(h >= height)
    return EmptyElem(width, h - height) above this
}

fun Elem.heightenD(h: Int): Elem {
    check(h >= height)
    return this above EmptyElem(width, h - height)
}

class BasicElem(override val content: List<String>) : ToStringElem(content)

open class CharElem(c: Char, width: Int, height: Int) :
    ToStringElem(Array(height) { c.toString().repeat(width) }.toList())

class EmptyElem(width: Int, height: Int) : CharElem(' ', width, height)

fun spiral(n: Int): Elem =
    if (n == 1) CharElem('*', 1, 1)
    else {
        val sub = spiral(n - 1)
        when (n % 4) {
            2 -> sub.widenL(n) above CharElem('*', n, 1)
            3 -> sub.heightenU(n) beside CharElem('*', 1, n)
            0 -> CharElem('*', n, 1) above sub.widenR(n)
            1 -> CharElem('*', 1, n) beside sub.heightenD(n)
            else -> error("fail")
        }
    }

fun main() {
    println(spiral(20))
}