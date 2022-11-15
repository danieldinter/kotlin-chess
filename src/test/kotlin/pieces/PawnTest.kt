package pieces

import gg.dani.chess.board.Board
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Bishop
import gg.dani.chess.pieces.Pawn
import gg.dani.chess.pieces.Rook
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class PawnTest : StringSpec({

    var emptyBoard: Board = Board(false)

    beforeEach {
        emptyBoard = Board(false)
    }

    "white pawn on starting position a2 can move to and only to empty squares a3 and a4" {
        val a2 = emptyBoard.getSquare("a2")
        val a3 = emptyBoard.getSquare("a3")
        val a4 = emptyBoard.getSquare("a4")
        val pawn = Pawn(Color.WHITE, a2)
        pawn.wasMoved shouldBe false
        pawn.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(a3, a4)
    }

    "black pawn on starting position e7 can move to and only to empty squares e6 and e5" {
        val e7 = emptyBoard.getSquare("e7")
        val e6 = emptyBoard.getSquare("e6")
        val e5 = emptyBoard.getSquare("e5")
        val pawn = Pawn(Color.BLACK, e7)
        pawn.wasMoved shouldBe false
        pawn.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(e6, e5)
    }

    "white pawn that moved from a2->a3 can only move to a4 afterwards" {
        val a2 = emptyBoard.getSquare("a2")
        val a3 = emptyBoard.getSquare("a3")
        val a4 = emptyBoard.getSquare("a4")
        val pawn = emptyBoard.initializePiece<Pawn>(Color.WHITE, a2)
        pawn.move(a3)
        pawn.wasMoved shouldBe true
        pawn.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(a4)
    }

    "white pawn on starting position a2 blocked by white rook on a3 can't move" {
        val a2 = emptyBoard.getSquare("a2")
        val a3 = emptyBoard.getSquare("a3")
        val pawn = emptyBoard.initializePiece<Pawn>(Color.WHITE, a2)
        emptyBoard.initializePiece<Rook>(Color.WHITE, a3)
        pawn.wasMoved shouldBe false
        pawn.getAccessibleSquares(emptyBoard).shouldBeEmpty()
    }

    "white pawn on starting position d2 facing black rook on d4 can move to and only to empty square d3" {
        val d2 = emptyBoard.getSquare("d2")
        val d3 = emptyBoard.getSquare("d3")
        val d4 = emptyBoard.getSquare("d4")
        val pawn = emptyBoard.initializePiece<Pawn>(Color.WHITE, d2)
        emptyBoard.initializePiece<Rook>(Color.BLACK, d4)
        pawn.wasMoved shouldBe false
        pawn.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(d3)
    }

    "white pawn on g4 and black pawn on g5 block each other so they can't move" {
        val whitePawn = emptyBoard.initializePiece<Pawn>(Color.WHITE, "g2")
        whitePawn.move(emptyBoard.getSquare("g4"))

        val blackPawn = emptyBoard.initializePiece<Pawn>(Color.BLACK, "g7")
        blackPawn.move(emptyBoard.getSquare("g5"))

        whitePawn.wasMoved shouldBe true
        blackPawn.wasMoved shouldBe true

        whitePawn.getAccessibleSquares(emptyBoard).shouldBeEmpty()
        blackPawn.getAccessibleSquares(emptyBoard).shouldBeEmpty()
    }

    "white pawn on starting position d2 facing black rook on d3 and black bishop on e3 can move to and only to capture black bishop on e3" {
        val d2 = emptyBoard.getSquare("d2")
        val d3 = emptyBoard.getSquare("d3")
        val e3 = emptyBoard.getSquare("e3")
        val pawn = emptyBoard.initializePiece<Pawn>(Color.WHITE, d2)
        emptyBoard.initializePiece<Rook>(Color.BLACK, d3)
        emptyBoard.initializePiece<Bishop>(Color.BLACK, e3)
        pawn.wasMoved shouldBe false
        pawn.getAccessibleSquares(emptyBoard).shouldContainExactlyInAnyOrder(e3)
    }

})