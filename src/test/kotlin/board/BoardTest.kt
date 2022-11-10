package board

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Knight
import gg.dani.chess.pieces.Piece
import gg.dani.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BoardTest {

    @Test
    fun testStandardBoard() {
        val board = Board()

        val whiteRook = testSquare(board, 'a', 1)
        assertTrue(whiteRook is Rook)
        assertTrue(whiteRook.color == Color.WHITE)

        val whiteKnight = testSquare(board, 'b', 1)
        assertTrue(whiteKnight is Knight)
        assertTrue(whiteKnight.color == Color.WHITE)
    }

    fun testSquare(board: Board, col: Char, row: Int): Piece {
        val s = board.getSquare(col, row)
        assertNotNull(s)
        assertNotNull(s.piece)
        assertTrue(s.occupied)
        return s.piece!!
    }
}