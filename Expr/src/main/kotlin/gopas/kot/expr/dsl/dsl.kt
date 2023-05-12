package gopas.kot.expr.dsl

import gopas.kot.expr.*
import gopas.kot.expr.Oper.MLT
import gopas.kot.expr.Oper.PLS
import java.util.*

fun Stack<Expr>.num(value: Int): Expr =
    push(Num(value))

fun Stack<Expr>.unop(op: Oper, builder: Stack<Expr>.() -> Unit): Expr {
    val oss = size
    return builder().run {
        check(this@unop.size - oss == 1)
        push(UnOp(op, pop()))
    }
}

fun Stack<Expr>.binop(op: Oper, builder: Stack<Expr>.() -> Unit): Expr {
    val oss = size
    builder()
    check(size - oss == 2)
    return push(BinOp(op, right = pop(), left = pop()))
}

fun main() {
    val e = Stack<Expr>()
            .binop(MLT) {
                num(1)
                unop(PLS) {
                    num(4)
                }
            }
    println(e.infix)
}
