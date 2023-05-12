package gopas.kot.custui

import javafx.scene.control.TextField
import tornadofx.*

class AddCust : Fragment("Add Customer") {
    override val root = form {
        lateinit var ntf: TextField
        lateinit var atf: TextField
        fieldset {
            field("Name") {
                ntf = textfield()
            }
            field("Addres") {
                atf = textfield()
            }
        }
        buttonbar {
            button("Cancel") {
                action {
                    close()
                }
            }
            button("Ok") {
                action {
                    custs.add(Cust(ntf.text, atf.text))
                    close()
                }
            }
        }
    }
}