package gopas.kot.expr.optim

import gopas.kot.expr.*
import gopas.kot.expr.Expr.Companion.ONE
import gopas.kot.expr.Expr.Companion.TWO
import gopas.kot.expr.Expr.Companion.ZERO
import gopas.kot.expr.Oper.*

interface ExprPatt {
    fun match(e: Expr): Boolean
}

open class UnOpPattCl(val op: Oper? = null, val oprndPatt: ExprPatt = UniVPatt) : ExprPatt {
    override fun match(e: Expr) = e is UnOp && e.op == op && oprndPatt.match(e.oprnd)
}

open class NumPattCl(val min: Int = Int.MIN_VALUE, val max: Int = Int.MAX_VALUE) : ExprPatt {
    override fun match(e: Expr) = e is Num && e.value in min..max
}

open class BinOpPattCl(
    val op: Oper? = null, val loprndPatt: ExprPatt = UniVPatt,
    val roprndPatt: ExprPatt = UniVPatt
) : ExprPatt {
    override fun match(e: Expr) = e is BinOp &&
            e.op == op && loprndPatt.match(e.left) && roprndPatt.match(e.right)
}


object NumPatt : NumPattCl() {
    override fun match(e: Expr) = e is Num
}

object ZeroPatt : NumPattCl(min = 0, max = 0)
object OnePatt : NumPattCl(min = 1, max = 1)

object UnPatt : UnOpPattCl()

object UnPlsPatt : UnOpPattCl(PLS)

object UnMnsPatt : UnOpPattCl(MNS)

object UnMnsMnsPatt : UnOpPattCl(MNS, UnMnsPatt)

object UnMnsNumPatt : UnOpPattCl(MNS, NumPatt)

object BinPatt : BinOpPattCl()

object ZeroPlsPatt : BinOpPattCl(PLS, loprndPatt = ZeroPatt)

object PlsZeroPatt : BinOpPattCl(PLS, roprndPatt = ZeroPatt)

object OneMltPatt : BinOpPattCl(MLT, loprndPatt = OnePatt)

object MltOnePatt : BinOpPattCl(MLT, roprndPatt = OnePatt)

object UniVPatt : ExprPatt {
    override fun match(e: Expr) = true
}

fun Expr.optim(): Expr = when {
    UnMnsNumPatt.match(this) -> Num(-((this as UnOp).oprnd as Num).value)
    UnMnsMnsPatt.match(this) -> ((this as UnOp).oprnd as UnOp).oprnd.optim()
    UnPlsPatt.match(this) -> (this as UnOp).oprnd.optim()
    UnPatt.match(this) -> (this as UnOp).copy(oprnd = oprnd.optim())
    ZeroPlsPatt.match(this) -> (this as BinOp).right.optim()
    PlsZeroPatt.match(this) -> (this as BinOp).left.optim()
    OneMltPatt.match(this) -> (this as BinOp).right.optim()
    MltOnePatt.match(this) -> (this as BinOp).left.optim()
    BinPatt.match(this) -> (this as BinOp).copy(left = left.optim(), right = right.optim())
    UniVPatt.match(this) -> this
    else -> error("fail")
}

fun main() {
    listOf<Expr>(
        UnOp(PLS, Var("x")),
        UnOp(MNS, TWO),
        UnOp(MNS, UnOp(MNS, Var("x"))),
        BinOp(PLS, ZERO, TWO),
        BinOp(PLS, TWO, ZERO),
        BinOp(MLT, ONE, TWO),
        BinOp(MLT, TWO, ONE)
    ).forEach {
        println("${it.infix}  ====  ${it.optim().infix}")
    }
}


