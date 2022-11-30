package gg.dani.chess.board

import gg.dani.chess.game.Checkmate
import gg.dani.chess.helpers.Color
import gg.dani.chess.logger
import gg.dani.chess.pieces.*

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
    var whitePieces: MutableList<Piece> = mutableListOf()
    var blackPieces: MutableList<Piece> = mutableListOf()

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
            initializeStandardPieces()

        logger.debug { "Board: $this" }
    }

    private fun initializeStandardPieces() {
        // Pawns
        for (col in 1..COLS) {
            initializePiece<Pawn>(Color.WHITE, Coordinate(col, 2))
            initializePiece<Pawn>(Color.BLACK, Coordinate(col, 7))
        }

        // Rooks
        initializePiece<Rook>(Color.WHITE, Coordinate(1, 1))
        initializePiece<Rook>(Color.WHITE, Coordinate(COLS, 1))
        initializePiece<Rook>(Color.BLACK, Coordinate(1, ROWS))
        initializePiece<Rook>(Color.BLACK, Coordinate(COLS, ROWS))

        // Knights
        initializePiece<Knight>(Color.WHITE, Coordinate(2, 1))
        initializePiece<Knight>(Color.WHITE, Coordinate(COLS - 1, 1))
        initializePiece<Knight>(Color.BLACK, Coordinate(2, ROWS))
        initializePiece<Knight>(Color.BLACK, Coordinate(COLS - 1, ROWS))

        // Bishops
        initializePiece<Bishop>(Color.WHITE, Coordinate(3, 1))
        initializePiece<Bishop>(Color.WHITE, Coordinate(COLS - 2, 1))
        initializePiece<Bishop>(Color.BLACK, Coordinate(3, ROWS))
        initializePiece<Bishop>(Color.BLACK, Coordinate(COLS - 2, ROWS))

        // Queens
        initializePiece<Queen>(Color.WHITE, Coordinate(4, 1))
        initializePiece<Queen>(Color.BLACK, Coordinate(4, ROWS))

        // Kings
        val whiteKing = initializePiece<King>(Color.WHITE, Coordinate(5, 1))
        val blackKing = initializePiece<King>(Color.BLACK, Coordinate(5, ROWS))

        // get all pieces from the board, separated by color
        /*whitePieces.addAll(boardState.values.filter { square ->
            square.piece != null && square.piece!!.color == Color.WHITE
        }.toList().map { item -> item.piece }.requireNoNulls())

        blackPieces.addAll(boardState.values.filter { square ->
            square.piece != null && square.piece!!.color == Color.BLACK
        }.toList().map { item -> item.piece }.requireNoNulls())*/

        cm = Checkmate(this, whiteKing, whitePieces, blackKing, blackPieces)
    }

    inline fun <reified T : Piece> initializePiece(color: Color, coordinate: String): T {
        return initializePiece(color, Coordinate(coordinate))
    }

    inline fun <reified T : Piece> initializePiece(color: Color, coordinate: Coordinate): T {
        val square = getSquare(coordinate)
        return initializePiece(color, square)
    }

    inline fun <reified T : Piece> initializePiece(color: Color, square: Square): T {
        val piece = Piece.create<T>(color, square)
        square.piece = piece

        if (color == Color.WHITE)
            whitePieces.add(piece)
        else
            blackPieces.add(piece)

        return piece
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
        return getSquare(Coordinate(square))
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