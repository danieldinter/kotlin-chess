package gg.dani.chess.board

class ColCoordinate(val value: Int) {

    constructor(ch: Char) : this(toInt(ch))

    fun asChar(): Char {
        return toChar(value)
    }

    fun asString(): String {
        return asChar().toString()
    }

    override fun toString(): String {
        return asString()
    }

    companion object Conversion {
        private const val letters = "abcdefgh"

        private fun toChar(value: Int): Char {
            return letters.substring(value - 1, value).first()
        }

        private fun toInt(value: Char): Int {
            return letters.indexOf(value) + 1
        }
    }
}