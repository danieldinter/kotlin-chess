package pieces

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Pawn
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

class RookTest : FreeSpec({

    var emptyBoard = Board(false)

    beforeEach {
        emptyBoard = Board(false)
    }

    "check movable squares on starting position for black/white rooks" {
        val data = table(
            headers("color", "starting position", "expected movable squares"),
            row(Color.WHITE, "a1", "b1, c1, d1, e1, f1, g1, a2, a3, a4, a5, a6, a7, a8"),
            row(Color.WHITE, "h1", "b1, c1, d1, e1, f1, g1, h2, h3, h4, h5, h6, h7, h8"),
            row(Color.BLACK, "a8", "b8, c8, d8, e8, f8, g8, a7, a6, a5, a4, a3, a2, a1"),
            row(Color.BLACK, "h8", "b8, c8, d8, e8, f8, g8, h7, h6, h5, h4, h3, h2, h1")
        )
        var rooks = mutableMapOf<Rook, String>()
        forAll(data) { color, sp, es ->
            rooks[emptyBoard.initializePiece<Rook>(color, sp)] = es
        }
        rooks.forEach { (piece, es) ->
            val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
            piece.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
        }
    }

    "white rook" - {
        mapOf(
            "a1" to "b1, c1, d1, e1, f1, g1, h1"
        ).forAll { (sp, es) ->
            "on square a1 blocked by white pawn on a2 can only move horizontally" {
                val rook = emptyBoard.initializePiece<Rook>(Color.WHITE, sp)
                emptyBoard.initializePiece<Pawn>(Color.WHITE, "a2")

                es.split(",").forEach {
                    emptyBoard.initializePiece<Rook>(Color.WHITE, it.trim())
                }

                rook.getAccessibleSquares(emptyBoard).shouldBeEmpty()
            }
        }
    }

})