package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

class Rook(color: Color, currentPosition: Square) : Piece(color, "Rook", currentPosition), LinearMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        return getAccessibleLinearSquares(board)
    }

}