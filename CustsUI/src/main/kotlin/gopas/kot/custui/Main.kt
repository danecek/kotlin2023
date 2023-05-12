package gopas.kot.custui

import javafx.scene.Parent
import tornadofx.*

class Main : View("Customers") {
    override val root: Parent = borderpane {
        top = menubar {
            menu("Customers") {
                item("Add") {
                    action {
                        find<AddCust>().openModal()
                    }
                }
            }
        }
        center<CustPanel>()
    }

}

class CustsApp : App(Main::class)

fun main(args: Array<String>) {
    launch<CustsApp>()
}