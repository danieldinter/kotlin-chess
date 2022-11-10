package gg.dani.chess.gui

import javafx.geometry.Pos
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment

open class CoordinateLabel : Label() {

    init {
        alignment = Pos.CENTER
        textAlignment = TextAlignment.CENTER
        contentDisplay = ContentDisplay.CENTER
        maxHeight = 100.0
        minWidth = 60.0
        font = Font("Arial", 30.0)
    }
}