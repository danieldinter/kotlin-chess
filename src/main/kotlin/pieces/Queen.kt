package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Class representing the Queen piece
 *
 * @constructor Create a new Queen piece
 *
 * @param color the color of the piece (black or white)
 * @param currentPosition the position on which the piece is located
 */
class Queen(color: Color, currentPosition: Square) : Piece(color, "Queen", currentPosition), LinearMovable,
    DiagonalMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        result.addAll(getAccessibleLinearSquares(board))
        result.addAll(getAccessibleDiagonalSquares(board))

        return result
    }

}