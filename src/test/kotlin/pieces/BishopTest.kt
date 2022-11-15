package pieces

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Bishop
import gg.dani.chess.pieces.Rook
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder

class BishopTest : FreeSpec({

    var emptyBoard = Board(false)

    beforeEach {
        emptyBoard = Board(false)
    }

    "check movable squares on starting position for all black/white bishops" {
        forAll(
            table(
                headers("color", "starting position", "expected movable squares"),
                row(Color.WHITE, "c1", "d2, e3, f4, g5, h6, b2, a3"),
                row(Color.WHITE, "f1", "g2, h3, e2, d3, c4, b5, a6"),
                row(Color.BLACK, "c8", "d7, e6, f5, g4, h3, b7, a6"),
                row(Color.BLACK, "f8", "g7, h6, e7, d6, c5, b4, a3")
            )
        ) { color, sp, es ->
            val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
            val bishop = emptyBoard.initializePiece<Bishop>(color, sp)
            bishop.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
        }
    }

    "black bishop" - {
        mapOf(
            "f6" to "g7, h8, g5, h4, e5, d4, c3, b2, a1, e7, d8"
        ).forAll { (sp, es) ->
            "on starting position $sp can move to and only to empty squares $es" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val bishop = emptyBoard.initializePiece<Bishop>(Color.BLACK, sp)
                bishop.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
        }
        mapOf(
            "d4" to "e3, e5, c3, c5"
        ).forAll { (p, es) ->
            "on $p surrounded by white rooks on diagonal squares $es can move to and only to surrounding diagonal squares" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val bishop = emptyBoard.initializePiece<Bishop>(Color.BLACK, p)
                expectedSquares.forEach { s ->
                    emptyBoard.initializePiece<Rook>(Color.WHITE, s)
                }
                bishop.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
        }
    }

})