package gopas.kot.custui


import tornadofx.*

class CustPanel : View() {

    override val root = tableview (custs) {
        readonlyColumn("Name", Cust::name)
        readonlyColumn("Address", Cust::addr)
        contextmenu {
            item("Delete") {
                action {
                   custs.remove(selectedItem)
                }
            }
        }
    }
}