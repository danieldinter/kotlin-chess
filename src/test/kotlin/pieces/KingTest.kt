package pieces

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Bishop
import gg.dani.chess.pieces.King
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder

class KingTest : FreeSpec({

    var emptyBoard = Board(false)

    beforeEach {
        emptyBoard = Board(false)
    }

    "check movable squares on starting position for black/white kings" {
        forAll(
            table(
                headers("color", "starting position", "expected movable squares"),
                row(Color.WHITE, "e1", "d1, d2, e2, f2, f1"),
                row(Color.BLACK, "e8", "d8, d7, e7, f7, f8")
            )
        ) { color, sp, es ->
            val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
            val king = emptyBoard.initializePiece<King>(color, sp)
            king.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
        }
    }

    "white king" - {
        mapOf(
            "d3" to "c2, c3, c4, d2, d4, e2, e3, e4"
        ).forAll { (sp, es) ->
            "on starting position $sp can move to and only to empty squares $es" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val king = emptyBoard.initializePiece<King>(Color.WHITE, sp)
                king.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
        }
    }

})