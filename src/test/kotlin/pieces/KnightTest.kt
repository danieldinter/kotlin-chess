package pieces

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Knight
import gg.dani.chess.pieces.Rook
import io.kotest.core.spec.style.FreeSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder

class KnightTest : FreeSpec({

    var emptyBoard = Board(false)

    beforeEach {
        emptyBoard = Board(false)
    }

    "check movable squares on starting position for black/white knights" {
        forAll(
            table(
                headers("color", "starting position", "expected movable squares"),
                row(Color.WHITE, "b1", "a3, c3, d2"),
                row(Color.WHITE, "g1", "h3, f3, e2"),
                row(Color.BLACK, "b8", "a6, c6, d7"),
                row(Color.BLACK, "g8", "h6, f6, e7")
            )
        ) { color, sp, es ->
            val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
            val knight = emptyBoard.initializePiece<Knight>(color, sp)
            knight.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
        }
    }

    "white knight" - {
        mapOf(
            "e4" to "f6, g5, g3, f2, d2, c3, c5, d6"
        ).forAll { (sp, es) ->
            "on starting position $sp can move to and only to empty squares $es" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val knight = emptyBoard.initializePiece<Knight>(Color.WHITE, sp)
                knight.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
            "on starting position $sp surrounded by white rooks can jump over the rooks and still move to and only to empty squares $es" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val knight = emptyBoard.initializePiece<Knight>(Color.WHITE, sp)

                emptyBoard.initializePiece<Rook>(Color.WHITE, "d5")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "e5")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "f5")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "d4")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "f4")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "d3")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "e3")
                emptyBoard.initializePiece<Rook>(Color.WHITE, "f3")

                knight.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
            "on starting position $sp surrounded by black rooks can jump over the rooks and still move to and only to empty squares $es" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val knight = emptyBoard.initializePiece<Knight>(Color.WHITE, sp)

                emptyBoard.initializePiece<Rook>(Color.BLACK, "d5")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "e5")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "f5")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "d4")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "f4")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "d3")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "e3")
                emptyBoard.initializePiece<Rook>(Color.BLACK, "f3")

                knight.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
            "on starting position $sp can still move to and only to empty squares $es if squares are occupied by black rooks" {
                val expectedSquares = es.split(",").map { emptyBoard.getSquare(it.trim()) }
                val knight = emptyBoard.initializePiece<Knight>(Color.WHITE, sp)

                es.split(",").forEach {
                    emptyBoard.initializePiece<Rook>(Color.BLACK, it.trim())
                }

                knight.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(expectedSquares)
            }
        }
    }

})