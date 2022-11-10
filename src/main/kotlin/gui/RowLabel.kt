package gg.dani.chess.gui

import gg.dani.chess.board.Board

class RowLabel(val row: Int) : CoordinateLabel() {

    init {
        text = "" + (Board.ROWS - row + 1)
    }
}