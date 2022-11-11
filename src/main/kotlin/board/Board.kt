package gg.dani.chess.board

import gg.dani.chess.game.Checkmate
import gg.dani.chess.helpers.Color
import gg.dani.chess.logger
import gg.dani.chess.pieces.*
import java.lang.IllegalArgumentException

/**
 * The game board (8x8 is chess standard size) consisting of all the squares and the black and white pieces located on the squares.
 * Board is represented by a coordinate system using a col(umn) (x-axis) and row (y-axis) coordinate.
 *
 * @constructor create a new board
 *
 * @param initializePieces if true places the standard lineup of pieces for both colors
 */
class Board(initializePieces: Boolean) {

    constructor() : this(true) // add default constructor without params

    lateinit var cm: Checkmate
    lateinit var whitePieces: MutableList<Piece>
    lateinit var blackPieces: MutableList<Piece>

    companion object {
        const val ROWS = 8
        const val COLS = 8
    }

    private var boardState: HashMap<Coordinate, Square> = HashMap()

    init {
        // create empty board
        for (col in 1..COLS) {
            for (row in 1..ROWS) {
                val c = Coordinate(col, row)
                this.boardState[c] = Square(c)
            }
        }

        // initialize pieces
        if (initializePieces)
            initialize()

        logger.debug { "Board: $this" }
    }

    private fun initialize() {
        // Pawns
        for (col in 1..COLS) {
            val s1 = getSquare(col, 2)
            s1.piece = Pawn(Color.WHITE, s1)

            val s2 = getSquare(col, 7)
            s2.piece = Pawn(Color.BLACK, s2)
        }

        // Rooks
        val rooks = mapOf(
            getSquare(1, 1) to Color.WHITE,
            getSquare(COLS, 1) to Color.WHITE,
            getSquare(1, ROWS) to Color.BLACK,
            getSquare(COLS, ROWS) to Color.BLACK
        )
        rooks.forEach { (s, color) -> s.piece = Rook(color, s) }

        // Knights
        val knights = mapOf(
            getSquare(2, 1) to Color.WHITE,
            getSquare(COLS - 1, 1) to Color.WHITE,
            getSquare(2, ROWS) to Color.BLACK,
            getSquare(COLS - 1, ROWS) to Color.BLACK
        )
        knights.forEach { (s, color) -> s.piece = Knight(color, s) }

        // Bishops
        val bishops = mapOf(
            getSquare(3, 1) to Color.WHITE,
            getSquare(COLS - 2, 1) to Color.WHITE,
            getSquare(3, ROWS) to Color.BLACK,
            getSquare(COLS - 2, ROWS) to Color.BLACK
        )
        bishops.forEach { (s, color) -> s.piece = Bishop(color, s) }

        // Queens
        val queens = mapOf(
            getSquare(4, 1) to Color.WHITE,
            getSquare(4, ROWS) to Color.BLACK
        )
        queens.forEach { (s, color) -> s.piece = Queen(color, s) }

        // Kings
        val s1 = getSquare(5, 1)
        val whiteKing = King(Color.WHITE, s1)
        s1.piece = whiteKing

        val s2 = getSquare(5, ROWS)
        val blackKing = King(Color.BLACK, s2)
        s2.piece = blackKing

        whitePieces = mutableListOf()
        blackPieces = mutableListOf()

        // get all pieces from the board, separated by color
        whitePieces.addAll(boardState.values.filter { square ->
            square.piece != null && square.piece!!.color == Color.WHITE
        }.toList().map { item -> item.piece }.requireNoNulls())

        blackPieces.addAll(boardState.values.filter { square ->
            square.piece != null && square.piece!!.color == Color.BLACK
        }.toList().map { item -> item.piece }.requireNoNulls())

        cm = Checkmate(this, whiteKing, whitePieces, blackKing, blackPieces)
    }

    /**
     * Get the square at a coordinate (row and col)
     *
     * @param col the column coordinate
     * @param row the row coordinate
     * @return the square at the coordinate
     */
    fun getSquare(col: Int, row: Int): Square {
        return getSquare(Coordinate(col, row))
    }

    /**
     * Get the square at a coordinate (row and col) where the column is represented by its letter (a to h)
     * Method is useful when using meaningful names for the squares (e.g. 'a1' instead of (1,1)), e.g. in test cases
     *
     * @param col the column coordinate a letter (a to h)
     * @param row the row coordinate
     * @return the square at the coordinate
     */
    fun getSquare(col: Char, row: Int): Square {
        return getSquare(Coordinate(col, row))
    }

    /**
     * Get the square at a coordinate where the coordinate is represented by a string (letter and number)
     * Method is useful when using meaningful names for the squares (e.g. "a1" instead of (1,1)), e.g. in test cases
     *
     * @param square the string name of the square (e.g. "a1")
     * @return the square at the coordinate
     * @throws IllegalArgumentException if the string is not exactly one letter and a number
     */
    fun getSquare(square: String): Square {
        val letters = ColCoordinate.Conversion.letters
        val intRange = (1..8)
        val first = square[0]
        val second = square[1].digitToInt()
        if(square.length == 2 && letters.contains(first) && intRange.contains(second))
            return getSquare(first, second)

        // else throw exception
        throw IllegalArgumentException("Square name has to be a Char in $letters plus an Integer in $intRange")
    }

    /**
     * Get the square at a coordinate where the coordinate is represented as a [Coordinate] object
     *
     * @param at the coordinates of the square
     * @return the square at the coordinate
     */
    fun getSquare(at: Coordinate): Square {
        return this.boardState[at]!!
    }

    /**
     * Get all squares of the board
     *
     * @return the squares of the board
     */
    fun getSquares(): HashMap<Coordinate, Square> {
        return boardState
    }

    override fun toString(): String {
        var res = ""
        for (row in ROWS downTo 1) {
            res += "[ "
            for (col in 1..COLS) {
                val c = Coordinate(col, row)
                res += this.boardState[c]
                if (col < COLS)
                    res += ", "
            }
            res += " ]\r\n"
        }
        return res
    }

}