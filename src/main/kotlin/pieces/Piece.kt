package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color
import gg.dani.chess.logger

abstract class Piece(var color: Color, private val name: String, var currentSquare: Square) {

    abstract fun getAccessibleSquares(board: Board): List<Square>

    fun getImageName(): String {
        return name.lowercase() + "_" + color.toString().lowercase() + ".png"
    }

    open fun move(to: Square): Boolean {
        return move(to, false)
    }

    open fun move(to: Square, testMove: Boolean): Boolean {
        return move(to, testMove, false)
    }

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
        currentSquare.piece = null
        currentSquare = to
        currentSquare.piece = this
        return true
    }

    override fun toString(): String {
        return this.name + "[" + this.color + "]"
    }
}