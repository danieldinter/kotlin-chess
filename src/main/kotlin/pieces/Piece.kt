package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color
import gg.dani.chess.logger

/**
 * Abstract class representing any piece
 *
 * @property color the color of the piece (black or white)
 * @property name the name of the piece (Pawn, King, etc.)
 * @property currentPosition the position on which the piece is located
 * @constructor create a new piece
 */
abstract class Piece(var color: Color, private val name: String, var currentPosition: Square) {

    companion object Factory {
        inline fun <reified T : Piece> create(color: Color, square: Square): T {
            return when (T::class) {
                Bishop::class -> Bishop(color, square) as T
                King::class -> King(color, square) as T
                Knight::class -> Knight(color, square) as T
                Pawn::class -> Pawn(color, square) as T
                Queen::class -> Queen(color, square) as T
                Rook::class -> Rook(color, square) as T
                else -> throw IllegalArgumentException("piece type ${T::class} is not known")
            }
        }
    }

    /**
     * Get the squares that are accessible by the piece
     *
     * @param board the board state
     * @return a list of the squares accessible by the piece
     */
    abstract fun getAccessibleSquares(board: Board): List<Square>

    /**
     * Get the image file name for the piece. The image file should exist in the resources path
     *
     * @return the image file name for the piece
     */
    fun getImageName(): String {
        return name.lowercase() + "_" + color.toString().lowercase() + ".png"
    }

    /**
     * Move the piece to the target square
     *
     * @param to the target square
     * @return true if piece is moved
     */
    open fun move(to: Square): Boolean {
        return move(to, false)
    }

    /**
     * Move the piece to the target square
     *
     * @param to the target square
     * @param testMove true if the move is a test move
     * @return true if piece is moved
     */
    open fun move(to: Square, testMove: Boolean): Boolean {
        return move(to, testMove, false)
    }

    /**
     * Move the piece to the target square
     *
     * @param to the target square
     * @param testMove true if the move is a test move
     * @param revert true if the move is a revert
     * @return true if piece is moved
     */
    open fun move(to: Square, testMove: Boolean, revert: Boolean): Boolean {
        if (testMove)
            if (revert)
                logger.debug { "revert test move: $this -> $to" }
            else
                logger.debug { "test move: $this -> $to" }
        else
            logger.debug { "move: $this -> $to" }

        // if there is a piece in 'to' and it's the same color, can't move
        if ((to.piece != null) && (to.piece!!.color == this.color))
            return false

        // move piece
        currentPosition.piece = null
        currentPosition = to
        currentPosition.piece = this
        return true
    }

    override fun toString(): String {
        return this.name + "[" + this.color + "]"
    }
}