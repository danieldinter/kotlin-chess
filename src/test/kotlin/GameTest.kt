import gg.dani.chess.Game
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class GameTest {

    @Test
    fun testGetLegalMoves() {
        val game = Game()
        val board = game.board

        // White always has the first turn in a new game
        assertTrue(game.playersTurn.color == Color.WHITE)

        val a1 = board.getSquare('a', 1)
        assertNotNull(a1)
        assertNotNull(a1.piece)
        val whiteRook = a1.piece!!
        assertTrue(a1.occupied)
        assertTrue(whiteRook is Rook)
        assertTrue(whiteRook.color == Color.WHITE)
        assertTrue(game.getLegalMoves(whiteRook)!!.isEmpty())
    }

    @Test
    fun testMovePiece() {
    }
}