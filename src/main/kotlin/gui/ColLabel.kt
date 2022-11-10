package gg.dani.chess.gui

import gg.dani.chess.board.ColCoordinate

class ColLabel(val col: Int) : CoordinateLabel() {

    init {
        text = ColCoordinate(col).asString()
    }

}