package gg.dani.chess.gui

import javafx.geometry.Pos
import javafx.scene.paint.Color
import javafx.scene.text.Font
import tornadofx.*

class MessageView : View() {

    var label = label {
        font = Font(14.0)
        textFill = Color.GREEN
    }

    override val root = vbox {
        primaryStage.x = 10.0
        primaryStage.y = 10.0
        setPrefSize(400.0, 400.0)
        alignment = Pos.CENTER

        add(label)

        button {
            text = "Close"
            action {
                close()
            }
        }
    }

    fun setMessage(message: String) {
        label.text = message
    }

}