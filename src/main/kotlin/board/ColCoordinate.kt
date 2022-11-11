package gg.dani.chess.board

/**
 * The column coordinate of a [Square]
 * Column coordinates are internally represented as integer and range from 1 to 8
 * However, there a helpful methods to work with the columns using their letters a to h
 *
 * @see [Coordinate]
 * @property value the [Int] value of the column
 * @constructor Create a new column coordinate
 */
class ColCoordinate(val value: Int) {

    constructor(ch: Char) : this(toInt(ch))

    /**
     * Return the letter representation of the column, that is one of the letters from a to h, as [Char]
     *
     * @return the letter ranging from a to h
     */
    fun asChar(): Char {
        return toChar(value)
    }

    /**
     * Return the letter representation of the column, that is one of the letters from a to h, as [String]
     *
     * @return the letter ranging from a to h
     */
    fun asString(): String {
        return asChar().toString()
    }

    override fun toString(): String {
        return asString()
    }

    companion object Conversion {
        const val letters = "abcdefgh"

        private fun toChar(value: Int): Char {
            return letters.substring(value - 1, value).first()
        }

        private fun toInt(value: Char): Int {
            return letters.indexOf(value) + 1
        }
    }
}