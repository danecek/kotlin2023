package custui

import javafx.scene.Parent
import tornadofx.Fragment
import tornadofx.fieldset
import tornadofx.form
import tornadofx.*

class AddCust : Fragment() {
    override val root = form {
        fieldset {
            field("Name") {
                textfield()
            }
            field("Addres") {
                textfield()
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

                    close()
                }
            }
        }
    }
}