package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

class Bishop(color: Color, currentPosition: Square) : Piece(color, "Bishop", currentPosition), DiagonalMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        return getAccessibleDiagonalSquares(board)
    }

}