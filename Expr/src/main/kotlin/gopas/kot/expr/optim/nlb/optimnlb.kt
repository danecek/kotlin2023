package gopas.kot.expr.optim.nlb

import gopas.kot.expr.*
import gopas.kot.expr.Expr.Companion.ONE

interface ExprPatt {
    fun match(e: Expr): Expr?
    fun matchB(e: Expr) = match(e) != null
}

open class NumPattCl(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : ExprPatt {
    override fun match(e: Expr): Num? = if (e is Num && e.value in min..max) e else null
}


open class UnOpPattCl(val op: Oper? = null, val oprndPatt: ExprPatt = UnivPatt) : ExprPatt {
    override fun match(e: Expr): UnOp? = if (e is UnOp && e.op != null
        && e.op == op
        && oprndPatt.matchB(e.oprnd)
    ) e else null
}

open class BinOpPattCl(
    val op: Oper? = null, val loprndPatt: ExprPatt = UnivPatt, val roprndPatt: ExprPatt = UnivPatt
) : ExprPatt {
    override fun match(e: Expr): BinOp? = if (e is BinOp && e.op != null &&
        e.op == op && loprndPatt.matchB(e.left) && roprndPatt.matchB(e.right)
    ) e else null
}

object UnivPatt : ExprPatt {
    override fun match(e: Expr) = e
}

object NumPatt : NumPattCl() {
    override fun match(e: Expr): Num? = if (e is Num) e else null
}

object ZeroPatt : NumPattCl(min = 0, max = 0)
object OnePatt : NumPattCl(min = 1, max = 1)

object UnPatt : UnOpPattCl()

object UnPlsPatt : UnOpPattCl(Oper.PLS)

object UnMnsPatt : UnOpPattCl(Oper.MNS)

object UnMnsMnsPatt : UnOpPattCl(Oper.MNS, UnMnsPatt)

object UnMnsNumPatt : UnOpPattCl(Oper.MNS, NumPatt)

object BinPatt : BinOpPattCl()

object ZeroPlsPatt : BinOpPattCl(Oper.PLS, loprndPatt = ZeroPatt)

object PlsZeroPatt : BinOpPattCl(Oper.PLS, roprndPatt = ZeroPatt)

object OneMltPatt : BinOpPattCl(Oper.MLT, loprndPatt = OnePatt)

object MltOnePatt : BinOpPattCl(Oper.MLT, roprndPatt = OnePatt)

fun Expr.optimN(): Expr =
    UnPlsPatt.match(this)?.oprnd?.optimN()
        ?: UnMnsMnsPatt.match(this)?.oprnd.let { it as? UnOp }?.oprnd?.optimN()
        ?: UnPatt.match(this)?.run {
            copy(oprnd = oprnd.optimN())
        }
        ?: ZeroPlsPatt.match(this)?.right?.optimN()
        ?: PlsZeroPatt.match(this)?.left?.optimN()
        ?: OneMltPatt.match(this)?.right?.optimN()
        ?: MltOnePatt.match(this)?.left?.optimN()
        ?: BinPatt.match(this)?.run {
            copy(left = left.optimN(), right = right.optimN())
        }
        ?: this

fun main() {
    listOf<Expr>(
        UnOp(Oper.PLS, Var("x")),
        UnOp(Oper.MNS, Expr.TWO),
        UnOp(Oper.MNS, UnOp(Oper.MNS, Var("x"))),
        BinOp(Oper.PLS, Expr.ZERO, Expr.TWO),
        BinOp(Oper.PLS, Expr.TWO, Expr.ZERO),
        BinOp(Oper.MLT, ONE, Expr.TWO),
        BinOp(Oper.MLT, Expr.TWO, ONE)
    ).forEach {
        println("${it.infix}  ====  ${it.optimN().infix}")
    }
}


