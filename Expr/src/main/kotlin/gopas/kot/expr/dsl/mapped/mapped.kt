package gopas.kot.expr.dsl.mapped

import gopas.kot.expr.*


operator fun Expr.unaryPlus() = UnOp(Oper.PLS, this)
operator fun Expr.plus(rop: Expr) = BinOp(Oper.PLS, this, rop)
operator fun Expr.plus(rop: Int) = BinOp(Oper.PLS, this, Num(rop))
operator fun Int.not() = Num(this)

fun main() {
    println( +!2 + 3)
    println( +2 + 3)
}