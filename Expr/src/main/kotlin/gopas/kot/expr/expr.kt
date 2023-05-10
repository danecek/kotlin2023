package gopas.kot.expr

interface Expr {
    val value: Int
    val infix: String
}

/*class Num3(_value: Int) : Expr {
    override val value = _value
}

class Num2(override val value: Int) : Expr*/

data class Num(override val value: Int) : Expr {
    override val infix = value.toString()

}

enum class Oper(val txt: String){
    PLS("+"), MLT("*");
    override fun toString(): String = txt
}

data class BinOp(val op: Oper, val left: Expr, val right: Expr): Expr {
    override val infix: String = "${left.infix} $op ${right.infix}"
    override val value = when (op) {
            Oper.PLS -> left.value + right.value
            Oper.MLT -> left.value * right.value
        }
}


fun main() {
    val e = BinOp(Oper.PLS, Num(1), Num(2))
    println("${e.infix} = ${e.value}")

}