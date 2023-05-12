package gopas.kot.custui

import tornadofx.asObservable

data class Cust(val name: String, val addr: String)

val custs = mutableListOf(Cust("Tom", "Prague")).asObservable()