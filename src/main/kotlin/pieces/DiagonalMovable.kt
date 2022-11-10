package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Coordinate
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Interface for a diagonal movable piece like a Bishop or Queen
 *
 * @constructor Create a diagonal movable piece
 */
interface DiagonalMovable {

    val color: Color
    var currentPosition: Square

    /**
     * Get diagonally accessible squares
     *
     * @param board the board state
     * @return a list of accessible squares
     */
    fun getAccessibleDiagonalSquares(board: Board): List<Square> {
        return getAccessibleDiagonalSquares(board, 8)
    }

    /**
     * Get diagonally accessible squares
     *
     * @param board the board state
     * @param stepSize the max number of squares a piece can move in one turn
     * @return a list of accessible squares
     */
    fun getAccessibleDiagonalSquares(board: Board, stepSize: Int): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        val col = currentPosition.col
        val row = currentPosition.row

        // check North East
        for (i in 1..stepSize) {
            val ne = Coordinate(col + i, row + i)

            if (ne.isWithinBoard()) {
                val s = board.getSquare(ne)
                if (s.occupied) {
                    if (s.piece!!.color == color) {
                        break
                    } else {
                        result.add(s)
                        break
                    }
                } else {
                    result.add(s)
                }
            } else {
                break
            }
        }

        // check South East
        for (i in 1..stepSize) {
            val se = Coordinate(col + i, row - i)

            if (se.isWithinBoard()) {
                val s = board.getSquare(se)
                if (s.occupied) {
                    if (s.piece!!.color == color) {
                        break
                    } else {
                        result.add(s)
                        break
                    }
                } else {
                    result.add(s)
                }
            } else {
                break
            }
        }

        // check South West
        for (i in 1..stepSize) {
            val sw = Coordinate(col - i, row - i)

            if (sw.isWithinBoard()) {
                val s = board.getSquare(sw)
                if (s.occupied) {
                    if (s.piece!!.color == color) {
                        break
                    } else {
                        result.add(s)
                        break
                    }
                } else {
                    result.add(s)
                }
            } else {
                break
            }
        }

        // check North West
        for (i in 1..stepSize) {
            val nw = Coordinate(col - i, row + i)

            if (nw.isWithinBoard()) {
                val s = board.getSquare(nw)
                if (s.occupied) {
                    if (s.piece!!.color == color) {
                        break
                    } else {
                        result.add(s)
                        break
                    }
                } else {
                    result.add(s)
                }
            } else {
                break
            }
        }

        return result
    }

}