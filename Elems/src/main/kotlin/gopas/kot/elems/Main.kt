package gopas.kot.elems

interface Elem {
    val content: List<String>
    val width: Int
        get() = if (content.isEmpty()) 0 else
            content.maxOf { it.length }// content.maxOf({ s: String -> s.length })
    val height: Int
        get() = content.size
}

open class ToStringElem(override val content: List<String>) : Elem {
    override fun toString() = content.joinToString(separator = "\n")
}

infix fun Elem.above(e: Elem): Elem {
    check(width == e.width) { "widths differ" }
    return BasicElem(content + e.content)
}// (this.content.plus(e.content))

infix fun Elem.beside(e: Elem): Elem {
    check(height == e.height) { "heights differ" }
    val zipped: List<Pair<String, String>> = content zip e.content
    return BasicElem(zipped.map { it.first + it.second })
}


class BasicElem(override val content: List<String>) : ToStringElem(content)

class CharElem(c: Char, width: Int, height: Int) :
    ToStringElem(Array(height) { c.toString().repeat(width) }.toList())

// fun spiral(n: Int) : Elem

fun main() {
    val be = BasicElem(listOf("xxx", "yyyzzzz"))
    val ce = CharElem('x', 5, 6)
    val ca = CharElem('*', 5, 6)
    // println(ce)
    println(ce above ca)//be.above(be))
    println(ce beside ca)//be.above(be))

}