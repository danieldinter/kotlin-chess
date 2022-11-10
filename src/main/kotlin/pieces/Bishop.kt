package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Class representing the Bishop piece
 *
 * @constructor Create a new Bishop piece
 *
 * @param color the color of the piece (black or white)
 * @param currentPosition the position on which the piece is located
 */
class Bishop(color: Color, currentPosition: Square) : Piece(color, "Bishop", currentPosition), DiagonalMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        return getAccessibleDiagonalSquares(board)
    }

}