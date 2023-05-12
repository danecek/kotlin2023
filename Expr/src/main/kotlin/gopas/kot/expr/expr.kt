package gopas.kot.expr

interface Expr {
    val value: Int
    val infix: String
    val pri: Int

    companion object {
        val One = Num(1)
    }
}

/*class Num3(_value: Int) : Expr {
    override val value = _value
}

class Num2(override val value: Int) : Expr*/

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
    override val infix: String = "${left.infix} $op ${right.infix}"
    override val pri = op.pri
    override val value: Int
        get() = when (op) {
            Oper.PLS -> left.value + right.value
            Oper.MNS -> left.value + right.value
            Oper.MLT -> left.value * right.value
        }
}

data class UnOp(val op: Oper, val oprnd: Expr) : Expr {
    override val infix: String = "$op${oprnd.infix}"
    override val pri = 0
    override val value: Int
        get() = when (op) {
            Oper.PLS -> oprnd.value
            Oper.MNS -> -oprnd.value
            else -> error("fail")
        }
}


fun main() {
    val e = BinOp(Oper.PLS, Num(1), Num(2))
    println("${e.infix} = ${e.value}")

}