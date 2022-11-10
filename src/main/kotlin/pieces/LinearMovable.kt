package gg.dani.chess.pieces

import gg.dani.chess.board.Board
import gg.dani.chess.board.Coordinate
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color

/**
 * Interface for a linear movable piece like a Rook or Queen
 *
 * @constructor Create a linear movable piece
 */
interface LinearMovable {

    val color: Color
    var currentPosition: Square

    /**
     * Get linearly accessible squares
     *
     * @param board the board state
     * @return a list of accessible squares
     */
    fun getAccessibleLinearSquares(board: Board): List<Square> {
        return getAccessibleLinearSquares(board, 8)
    }

    /**
     * Get linearly accessible squares
     *
     * @param board the board state
     * @param stepSize the max number of squares a piece can move in one turn
     * @return a list of accessible squares
     */
    fun getAccessibleLinearSquares(board: Board, stepSize: Int): List<Square> {
        val result: MutableList<Square> = mutableListOf()

        val col = currentPosition.col
        val row = currentPosition.row

        // check North
        for (i in 1..stepSize) {
            val north = Coordinate(col, row + i)

            if (north.isWithinBoard()) {
                val s = board.getSquare(north)
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

        // check East
        for (i in 1..stepSize) {
            val east = Coordinate(col + i, row)

            if (east.isWithinBoard()) {
                val s = board.getSquare(east)
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

        // check South
        for (i in 1..stepSize) {
            val south = Coordinate(col, row - i)

            if (south.isWithinBoard()) {
                val s = board.getSquare(south)
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

        // check West
        for (i in 1..stepSize) {
            val west = Coordinate(col - i, row)

            if (west.isWithinBoard()) {
                val s = board.getSquare(west)
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