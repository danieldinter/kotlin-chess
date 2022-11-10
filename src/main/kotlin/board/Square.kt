package gg.dani.chess.board

import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Piece
import java.util.*

class Square(val coordinate: Coordinate) {

    val color: Color
    var piece: Piece? = null

    val row: Int get() = this.coordinate.row.value
    val col: Int get() = this.coordinate.col.value

    val occupied: Boolean
        get() = (piece != null)

    init { // TODO: also implemented in gui
        if ((coordinate.col.value + coordinate.row.value) % 2 == 0)
            this.color = Color.WHITE
        else
            this.color = Color.BLACK
    }

    constructor (col: Int, row: Int) : this(Coordinate(col, row))

    override fun toString(): String {
        return coordinate.toString() + "|" + piece?.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Square)
            return this.coordinate == other.coordinate

        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(coordinate.hashCode())
    }
}