package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Coordinate
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

class Pawn(color: Color, currentPosition: Square) : Piece(color, "Pawn", currentPosition) {

    private var wasMoved = false

    override fun getAccessibleSquares(board: Board): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        // linear fields
        if (color == Color.WHITE) {
            val c = Coordinate(currentSquare.col, currentSquare.row + 1)
            if (c.isWithinBoard()) {
                val squareInFront = board.getSquare(c)
                // when pawn was not moved and square is not occupied then add row + 2 to the result
                if (!wasMoved && !squareInFront.occupied)
                    result.add(board.getSquare(Coordinate(currentSquare.col, currentSquare.row + 2)))

                if (!squareInFront.occupied)
                    result.add(squareInFront)
            }
        } else {
            val c = Coordinate(currentSquare.col, currentSquare.row - 1)
            if (c.isWithinBoard()) {
                val squareInFront = board.getSquare(c)
                // when pawn was not moved and square is not occupied then add row + 2 to the result
                if (!wasMoved && !squareInFront.occupied)
                    result.add(board.getSquare(Coordinate(currentSquare.col, currentSquare.row - 2)))

                if (!squareInFront.occupied)
                    result.add(squareInFront)
            }
        }

        val moves: MutableList<Coordinate> = mutableListOf()
        // diagonal fields
        if (color == Color.WHITE) {
            moves.add(Coordinate(currentSquare.col + 1, currentSquare.row + 1))
            moves.add(Coordinate(currentSquare.col - 1, currentSquare.row + 1))
        } else {
            moves.add(Coordinate(currentSquare.col - 1, currentSquare.row - 1))
            moves.add(Coordinate(currentSquare.col + 1, currentSquare.row - 1))
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