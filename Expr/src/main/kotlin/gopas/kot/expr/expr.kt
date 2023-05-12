package gopas.kot.expr

import gopas.kot.expr.Oper.*
import gopas.kot.expr.Expr.Companion.ONE
import gopas.kot.expr.Expr.Companion.TWO

interface Expr {
    val value: Int
    val infix: String
    val pri: Int

    companion object {
        val ZERO = Num(0)
        val ONE = Num(1)
        val TWO = Num(2)
    }
}

data class Num(override val value: Int) : Expr {
    override val infix = value.toString()
    override val pri = 0
}

data class Var(val name: String) : Expr {
    override val value: Int
        get() = throw RuntimeException()
    override val infix: String = name
    override val pri = 0
}

enum class Oper(val txt: String, val pri: Int) {
    PLS("+", 2), MNS("-", 2), MLT("*", 1);

    override fun toString(): String = txt
}

data class BinOp(val op: Oper, val left: Expr, val right: Expr) : Expr {
    override val infix: String by lazy {
        val lo = if (op.pri < left.pri)
            "(${left.infix})"
        else "${left.infix}"
        val ro = if (op.pri < right.pri)
            "(${right.infix})"
        else "${right.infix}"
        "$lo $op $ro"
    }

    override val pri = op.pri
    override val value: Int
        get() = when (op) {
            PLS -> left.value + right.value
            MNS -> left.value - right.value
            MLT -> left.value * right.value
        }
}

data class UnOp(val op: Oper, val oprnd: Expr) : Expr {
    override val infix: String = "$op${oprnd.infix}"
    override val pri = 0
    override val value: Int
        get() = when (op) {
            PLS -> oprnd.value
            MNS -> -oprnd.value
            else -> error("fail")
        }
}


fun main() {
    listOf(
        BinOp(PLS, BinOp(MLT, TWO, ONE), BinOp(MLT, TWO, ONE)),
        BinOp(MLT, BinOp(PLS, TWO, ONE), BinOp(MNS, TWO, ONE))
    ).forEach {
        println("${it.infix} = ${it.value}")
    }
}