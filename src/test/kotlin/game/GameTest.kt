package game

import gg.dani.chess.game.Checkmate
import gg.dani.chess.game.Game
import gg.dani.chess.helpers.Color
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class GameKotest : StringSpec({

    val game = Game()

    "fools mate" {

        val board = game.board

        val f2 = board.getSquare("f2")
        val f3 = board.getSquare("f3")
        val e7 = board.getSquare("e7")
        val e6 = board.getSquare("e6")
        val g2 = board.getSquare("g2")
        val g4 = board.getSquare("g4")
        val d8 = board.getSquare("d8")
        val h4 = board.getSquare("h4")

        // f2 -> f3
        game.playersTurn.color shouldBe Color.WHITE
        game.movePiece(f2.piece!!, f3)

        noCheck(board.cm)

        // e7 -> e6
        game.playersTurn.color shouldBe Color.BLACK
        game.movePiece(e7.piece!!, e6)

        noCheck(board.cm)

        // g2 -> g4
        game.playersTurn.color shouldBe Color.WHITE
        game.movePiece(g2.piece!!, g4)

        noCheck(board.cm)

        // d8 -> h4
        game.playersTurn.color shouldBe Color.BLACK
        game.movePiece(d8.piece!!, h4)

        board.cm.isBlackChecked() shouldBe false
        board.cm.isBlackCheckmated() shouldBe false

        board.cm.isWhiteChecked() shouldBe true
        board.cm.isWhiteCheckmated() shouldBe true

    }

})

fun noCheck(cm: Checkmate) {
    cm.isBlackChecked() shouldBe false
    cm.isBlackCheckmated() shouldBe false
    cm.isWhiteChecked() shouldBe false
    cm.isWhiteCheckmated() shouldBe false
}