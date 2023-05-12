package gopas.rat

data class Rat(val n: Int, val d: Int) {

    constructor(n: Int) : this(n, 1)

    init {
        check(d !=0) {" zero denom "}
    }

    val norm : Rat by lazy {
        val gcd = gcd(n, d)
        Rat(n/ gcd, d/gcd)
    }


    fun inv() = Rat(d, n)
    operator fun times(r: Rat) = Rat(n * r.n, d * r.d)
    operator fun times(r: Int) = Rat(n * r, d * r)
    operator fun div(r: Rat) = this * r.inv()
    operator fun div(r: Int) = Rat(n, d * r)
    override fun toString() = "$n/$d"

    override fun equals(other: Any?): Boolean {
         if (this === other) return true
        if (other !is Rat) return false
        return norm.n == other.norm.n &&  norm.d == other.norm.d
    }

    override fun hashCode(): Int = norm.n * norm.d

    companion object {
        fun gcd(n: Int, d: Int) : Int =
            if (d == 0) n
            else gcd(d, n % d)
    }

}

operator fun Int.not() = Rat(this)

fun main(args: Array<String>) {
    val r1 = Rat(1, 2)
    val r2 = Rat(2, 4)
    println("$r2 =  ${r1.norm}")
    println(r1 * r1)
    println(r1 == r2)
    println(!3/2)
}