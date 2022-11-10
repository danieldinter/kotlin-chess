package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Coordinate
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Class representing the Pawn piece
 *
 * @constructor Create a new Pawn piece
 *
 * @param color the color of the piece (black or white)
 * @param currentPosition the position on which the piece is located
 */
class Pawn(color: Color, currentPosition: Square) : Piece(color, "Pawn", currentPosition) {

    private var wasMoved = false

    override fun getAccessibleSquares(board: Board): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        // linear fields
        if (color == Color.WHITE) {
            val c = Coordinate(currentPosition.col, currentPosition.row + 1)
            if (c.isWithinBoard()) {
                val squareInFront = board.getSquare(c)
                // when pawn was not moved and square is not occupied then add row + 2 to the result
                if (!wasMoved && !squareInFront.occupied)
                    result.add(board.getSquare(Coordinate(currentPosition.col, currentPosition.row + 2)))

                if (!squareInFront.occupied)
                    result.add(squareInFront)
            }
        } else {
            val c = Coordinate(currentPosition.col, currentPosition.row - 1)
            if (c.isWithinBoard()) {
                val squareInFront = board.getSquare(c)
                // when pawn was not moved and square is not occupied then add row + 2 to the result
                if (!wasMoved && !squareInFront.occupied)
                    result.add(board.getSquare(Coordinate(currentPosition.col, currentPosition.row - 2)))

                if (!squareInFront.occupied)
                    result.add(squareInFront)
            }
        }

        val moves: MutableList<Coordinate> = mutableListOf()
        // diagonal fields
        if (color == Color.WHITE) {
            moves.add(Coordinate(currentPosition.col + 1, currentPosition.row + 1))
            moves.add(Coordinate(currentPosition.col - 1, currentPosition.row + 1))
        } else {
            moves.add(Coordinate(currentPosition.col - 1, currentPosition.row - 1))
            moves.add(Coordinate(currentPosition.col + 1, currentPosition.row - 1))
        }

        moves.forEach { c ->
            if (c.isWithinBoard()) {
                val square = board.getSquare(c)
                if (square.piece != null && square.piece!!.color != this.color)
                    result.add(square)
            }
        }

        // @TODO: change pawn vs other piece if end of field is reached
        // @TODO: en passant

        return result
    }

    override fun move(to: Square): Boolean {
        wasMoved = true
        return super.move(to)
    }

}