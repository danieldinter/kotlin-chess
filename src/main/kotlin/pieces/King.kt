package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Class representing the King piece
 *
 * @constructor Create a new King piece
 *
 * @param color the color of the piece (black or white)
 * @param currentPosition the position on which the piece is located
 */
class King(color: Color, currentPosition: Square) : Piece(color, "King", currentPosition), LinearMovable,
    DiagonalMovable {

    override fun getAccessibleSquares(board: Board): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        // move along linear squares
        result.addAll(getAccessibleLinearSquares(board, 1))

        // move along diagonal squares
        result.addAll(getAccessibleDiagonalSquares(board, 1))

        // @TODO: Implement Castling / Rochade

        return result
    }

}