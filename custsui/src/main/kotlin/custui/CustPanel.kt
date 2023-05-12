package custui

import javafx.scene.Parent
import tornadofx.View
import tornadofx.asObservable
import tornadofx.readonlyColumn
import tornadofx.tableview

data class Cust(val name: String, val addr: String)
val custs = mutableListOf(Cust("Tom", "Prague"))
class CustPanel : View() {

    override val root = tableview (custs.asObservable()) {
        readonlyColumn("Name", Cust::name)
        readonlyColumn("Address", Cust::addr)

    }
}