package gg.dani.chess.gui

import javafx.scene.control.Label
import javafx.scene.text.Font

class PlayerLabel(text: String) : Label(text) {

    init {
        font = Font("Arial", 30.0)
    }

}