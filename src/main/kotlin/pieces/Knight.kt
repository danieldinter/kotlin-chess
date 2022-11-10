package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Coordinate
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

class Knight(color: Color, currentPosition: Square) : Piece(color, "Knight", currentPosition) {

    override fun getAccessibleSquares(board: Board): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        val moves: MutableList<Coordinate> = mutableListOf()

        // 1 right, 2 up
        moves.add(Coordinate(currentSquare.col + 1, currentSquare.row + 2))

        // 2 right, 1 up
        moves.add(Coordinate(currentSquare.col + 2, currentSquare.row + 1))

        // 1 right, 2 down
        moves.add(Coordinate(currentSquare.col + 1, currentSquare.row - 2))

        // 2 right, 1 down
        moves.add(Coordinate(currentSquare.col + 2, currentSquare.row - 1))

        // 1 left, 2 up
        moves.add(Coordinate(currentSquare.col - 1, currentSquare.row + 2))

        // 2 left, 1 up
        moves.add(Coordinate(currentSquare.col - 2, currentSquare.row + 1))

        // 1 left, 2 down
        moves.add(Coordinate(currentSquare.col - 1, currentSquare.row - 2))

        // 2 left, 1 down
        moves.add(Coordinate(currentSquare.col - 2, currentSquare.row - 1))

        // check:
        //  - coordinate of move within board
        //  - only squares that are free or if occupied, then occupied by other color
        moves.forEach { c ->
            if (c.isWithinBoard()) {
                val square = board.getSquare(c)
                if (square.piece == null || square.piece!!.color != this.color)
                    result.add(square)
            }
        }

        return result
    }

}