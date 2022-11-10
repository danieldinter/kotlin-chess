package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

class Queen(color: Color, currentPosition: Square) : Piece(color, "Queen", currentPosition), LinearMovable,
    DiagonalMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        result.addAll(getAccessibleLinearSquares(board))
        result.addAll(getAccessibleDiagonalSquares(board))

        return result
    }

}