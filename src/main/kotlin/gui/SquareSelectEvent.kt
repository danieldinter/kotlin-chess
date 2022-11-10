package gg.dani.chess.gui

import gg.dani.chess.board.Square
import gg.dani.chess.logger
import tornadofx.FXEvent

class SquareSelectEvent(val square: Square?) : FXEvent() {
    init {
        logger.debug { "SquareSelectEvent initialized for square: ${square.toString()}" }
    }
}