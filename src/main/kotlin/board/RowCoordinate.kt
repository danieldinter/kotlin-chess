package gg.dani.chess.board

/**
 * The row coordinate of a [Square]
 * Row coordinates are internally represented as integer and range from 1 to 8
 *
 * @see [Coordinate]
 * @property value the [Int] value of the row
 * @constructor Create a new row coordinate
 */
class RowCoordinate(val value: Int) {

    override fun toString(): String {
        return value.toString()
    }

}