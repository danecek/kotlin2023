package gopas.kot.expr.dsl

import gopas.kot.expr.*
import java.util.*

fun Stack<Expr>.num(value: Int): Expr {
    val n = Num(value)
    push(n)
    return n
}

fun Stack<Expr>.unop(op: Oper, builder: Stack<Expr>.() -> Unit) =
        builder().run { push(UnOp(op, pop())) }

fun Stack<Expr>.binop(op: Oper, builder: Stack<Expr>.() -> Unit): Expr {
    builder()
    val bo = BinOp(op, pop(), pop())
    push(bo)
    return bo
}

fun main() {
    val e = Stack<Expr>().binop(Oper.MNS) {
        num(1)
        unop(Oper.PLS) {
            num(4)
        }
    }
    println(e)
}
