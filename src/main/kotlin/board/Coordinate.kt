package gg.dani.chess.board

import java.util.*

class Coordinate {

    val col: ColCoordinate
    val row: RowCoordinate

    constructor(col: Int, row: Int) {
        this.col = ColCoordinate(col)
        this.row = RowCoordinate(row)
    }

    constructor(col: Char, row: Int) {
        this.col = ColCoordinate(col)
        this.row = RowCoordinate(row)
    }

    fun isWithinBoard(): Boolean {
        return (col.value <= Board.COLS) && (row.value <= Board.ROWS) && (col.value >= 1) && (row.value >= 1)
    }

    override fun toString(): String {
        return col.toString() + row.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Coordinate)
            return this.col.value == other.col.value && this.row.value == other.row.value

        return false
    }

    override fun hashCode(): Int {
        return Objects.hash(col.value, row.value)
    }

}