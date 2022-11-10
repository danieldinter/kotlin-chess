package board

import gg.dani.chess.board.Board
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EmptyBoardTest {

    private val emptyBoard = Board(false)

    @Test
    fun testNoBlackPiecesUninitialized() {
        assertThrows(UninitializedPropertyAccessException::class.java) { emptyBoard.blackPieces }
    }

    @Test
    fun testWhitePiecesUninitialized() {
        assertThrows(UninitializedPropertyAccessException::class.java) { emptyBoard.whitePieces }
    }

    @Test
    fun testSquaresNoPieces() {
        emptyBoard.getSquares().values.forEach { square ->
            assertNull(square.piece)
        }
    }

    @Test
    fun testSquaresNotOccupied() {
        emptyBoard.getSquares().values.forEach { square ->
            assertFalse(square.occupied)
        }
    }

}