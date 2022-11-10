package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Class representing the Rook piece
 *
 * @constructor Create a new Rook piece
 *
 * @param color the color of the piece (black or white)
 * @param currentPosition the position on which the piece is located
 */
class Rook(color: Color, currentPosition: Square) : Piece(color, "Rook", currentPosition), LinearMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        return getAccessibleLinearSquares(board)
    }

}