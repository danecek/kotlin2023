package gopas.kot.expr.optim.nlb

import gopas.kot.expr.Expr
import gopas.kot.expr.Expr.Companion.ONE
import gopas.kot.expr.Num
import gopas.kot.expr.Oper
import gopas.kot.expr.UnOp

interface ExprPatt {
    fun match(e: Expr): Expr?
    fun matchB(e: Expr) = match(e) !=null
}

open class UnOpPattCl(val op: Oper?=null, val oprndPatt: ExprPatt = UniVPatt) : ExprPatt {
    override fun match(e: Expr): UnOp?   = if (e is UnOp && e.op == op && oprndPatt.matchB(e.oprnd)) e else null
}
object NumPatt : ExprPatt {
    override fun match(e: Expr): Num? = e as? Num
}

object UnPatt : UnOpPattCl()

object UnPlSPatt : UnOpPattCl(Oper.PLS, UniVPatt)
object UnMnsNumPatt : UnOpPattCl(Oper.MNS, NumPatt)

object UniVPatt : ExprPatt {
    override fun match(e: Expr) = e
}

/*
val univPat = object : ExprPatt {
    override fun match(e: Expr) = true
}
*/

fun Expr.optimN(): Expr =
    UnPlSPatt.match(this)?.oprnd?.optimN() ?:
    this

/*    UnMnsNumPatt.match(this) -> Num(-((this as UnOp).oprnd as Num).value)
    UnPlSPatt.match(this) -> (this as UnOp).oprnd.optim()
    UnPatt.match(this) -> (this as UnOp).run { 
        copy(oprnd = this.oprnd.optim()) }///   .copy(oprnd = (this as UnOp).oprnd.optim())
    UniVPatt.match(this) -> this
    else -> error("fail")
    */


fun main() {
    listOf<Expr>(
        ONE,
        UnOp(Oper.PLS, Num(2)),
        UnOp(Oper.MNS, Num(2)),
        UnOp(Oper.PLS, UnOp(Oper.PLS, Num(2)))
    ).forEach {
        println("$it  ====  ${it.optimN()}")
    }
}


