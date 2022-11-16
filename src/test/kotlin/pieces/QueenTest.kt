package pieces

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Queen
import gg.dani.chess.pieces.Rook
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder

class QueenTest : FreeSpec({

    var emptyBoard = Board(false)

    beforeEach {
        emptyBoard = Board(false)
    }

    "check movable squares on starting position for black/white queen" {
        forAll(
            table(
                headers("color", "starting position", "expected movable squares"),
                row(Color.WHITE, "d1", "a1, b1, c1, e1, f1, g1, h1, c2, b3, a4, e2, f3, g4, h5, d2, d3, d4, d5, d6, d7, d8"),
                row(Color.BLACK, "d8", "a8, b8, c8, e8, f8, g8, h8, c7, b6, a5, e7, f6, g5, h4, d7, d6, d5, d4, d3, d2, d1")
            )
        ) { color, sp, es ->
            val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
            val queen = emptyBoard.initializePiece<Queen>(color, sp)
            queen.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
        }
    }

    "white queen" - {
        mapOf(
            "e4" to "d5, e5, f5, d4, f4, d3, e3, f3"
        ).forAll { (sp, es) ->
            "on position $sp surrounded by white rooks cannot move at all" {
                val queen = emptyBoard.initializePiece<Queen>(Color.WHITE, sp)

                es.split(",").forEach {
                    emptyBoard.initializePiece<Rook>(Color.WHITE, it.trim())
                }

                queen.getAccessibleSquares(emptyBoard).shouldBeEmpty()
            }
            "on position $sp surrounded by black rooks can move to and only to occupied squares $es to capture one of the rooks" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val queen = emptyBoard.initializePiece<Queen>(Color.WHITE, sp)

                es.split(",").forEach {
                    emptyBoard.initializePiece<Rook>(Color.BLACK, it.trim())
                }

                queen.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
        }
    }

})