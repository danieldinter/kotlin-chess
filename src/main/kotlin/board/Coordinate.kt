package gg.dani.chess.board

import java.util.*

/**
 * Coordinate that represents the position of a [Square] on the [Board]. The [Board] is represented as a 8x8 grid of squares. A particular square is addressed by its column (x-axis) and row (y-axis) value, each ranging from 1 to 8.
 *
 * @constructor Create a new Coordinate
 */
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

    constructor(square: String) {
        val letters = ColCoordinate.letters
        val intRange = (1..8)
        val first = square[0]
        val second = square[1].digitToInt()
        if (square.length == 2 && letters.contains(first) && intRange.contains(second)) {
            this.col = ColCoordinate(first)
            this.row = RowCoordinate(second)
        } else throw IllegalArgumentException("Square name has to be a Char in $letters plus an Integer in $intRange")
    }

    /**
     * Check if the coordinate is within the bounds of the board.
     * On a standard board the column and row values range from 1 to 8. The max number of columns is given by [Board.COLS] and the max number of rows is given by [Board.ROWS].
     *
     * @return true if the coordinate is within the bounds of the board
     */
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