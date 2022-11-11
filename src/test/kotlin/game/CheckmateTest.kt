package game

import gg.dani.chess.game.Checkmate
import gg.dani.chess.board.Board
import gg.dani.chess.board.Coordinate
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CheckmateTest {

    @Test
    fun testSimpleCheck() {
        val emptyBoard = Board(false)
        val squares = emptyBoard.getSquares()

        val e2 = squares[Coordinate('e', 2)]!!
        assertNotNull(e2) // if square does not exist fail test
        val whiteKing = King(Color.WHITE, e2)
        e2.piece = whiteKing

        val e8 = squares[Coordinate('e', 8)]!!
        assertNotNull(e8) // if square does not exist fail test
        val whiteRook = Rook(Color.WHITE, e8)
        e8.piece = whiteRook

        val a8 = squares[Coordinate('a', 8)]!!
        assertNotNull(a8) // if square does not exist fail test
        val blackKing = King(Color.BLACK, a8)
        a8.piece = blackKing

        val cm =
            Checkmate(emptyBoard, whiteKing, mutableListOf(whiteKing, whiteRook), blackKing, mutableListOf(blackKing))

        println(emptyBoard.toString())
        println(cm.toString())

        assertTrue(cm.isBlackChecked())
    }

    @Test
    fun testSimpleCheckmate() {
        val emptyBoard = Board(false)
        val squares = emptyBoard.getSquares()

        val e2 = squares[Coordinate('e', 2)]!!
        assertNotNull(e2) // if square does not exist fail test
        val whiteKing = King(Color.WHITE, e2)
        e2.piece = whiteKing

        val e7 = squares[Coordinate('e', 7)]!!
        assertNotNull(e7) // if square does not exist fail test
        val whiteRook = Rook(Color.WHITE, e7)
        e7.piece = whiteRook

        val b7 = squares[Coordinate('b', 7)]!!
        assertNotNull(b7) // if square does not exist fail test
        val whiteQueen = Queen(Color.WHITE, b7)
        b7.piece = whiteQueen

        val a8 = squares[Coordinate('a', 8)]!!
        assertNotNull(a8) // if square does not exist fail test
        val blackKing = King(Color.BLACK, a8)
        a8.piece = blackKing

        val cm = Checkmate(
            emptyBoard,
            whiteKing,
            mutableListOf(whiteKing, whiteRook, whiteQueen),
            blackKing,
            mutableListOf(blackKing)
        )

        println(emptyBoard.toString())
        println(cm.toString())

        assertTrue(cm.isBlackChecked())
        assertTrue(cm.isBlackCheckmated())
    }

    @Test
    fun testTwoRooksCheckmateBlocked() {
        val emptyBoard = Board(false)
        val squares = emptyBoard.getSquares()

        val f5 = squares[Coordinate('f', 5)]!!
        assertNotNull(f5) // if square does not exist fail test
        val whiteKing = King(Color.WHITE, f5)
        f5.piece = whiteKing

        val g7 = squares[Coordinate('g', 7)]!!
        assertNotNull(g7) // if square does not exist fail test
        val whiteRook = Rook(Color.WHITE, g7)
        g7.piece = whiteRook

        val g8 = squares[Coordinate('g', 8)]!!
        assertNotNull(g8) // if square does not exist fail test
        val whiteRookTwo = Rook(Color.WHITE, g8)
        g8.piece = whiteRookTwo

        val d8 = squares[Coordinate('d', 8)]!!
        assertNotNull(d8) // if square does not exist fail test
        val blackKing = King(Color.BLACK, d8)
        d8.piece = blackKing

        val d7 = squares[Coordinate('d', 7)]!!
        assertNotNull(d7) // if square does not exist fail test
        val blackPawn = Pawn(Color.BLACK, d7)
        d7.piece = blackPawn

        val cm = Checkmate(
            emptyBoard,
            whiteKing,
            mutableListOf(whiteKing, whiteRook, whiteRookTwo),
            blackKing,
            mutableListOf(blackKing, blackPawn)
        )

        println(emptyBoard.toString())
        println(cm.toString())

        assertTrue(cm.isBlackChecked())
        assertFalse(cm.isBlackCheckmated())
    }

    @Test
    fun testTwoRooksCheckmateHasToBlock() {
        val emptyBoard = Board(false)
        val squares = emptyBoard.getSquares()

        val f5 = squares[Coordinate('f', 5)]!!
        assertNotNull(f5) // if square does not exist fail test
        val whiteKing = King(Color.WHITE, f5)
        f5.piece = whiteKing

        val g7 = squares[Coordinate('g', 7)]!!
        assertNotNull(g7) // if square does not exist fail test
        val whiteRook = Rook(Color.WHITE, g7)
        g7.piece = whiteRook

        val g8 = squares[Coordinate('g', 8)]!!
        assertNotNull(g8) // if square does not exist fail test
        val whiteRookTwo = Rook(Color.WHITE, g8)
        g8.piece = whiteRookTwo

        val d8 = squares[Coordinate('d', 8)]!!
        assertNotNull(d8) // if square does not exist fail test
        val blackKing = King(Color.BLACK, d8)
        d8.piece = blackKing

        val b5 = squares[Coordinate('b', 5)]!!
        assertNotNull(b5) // if square does not exist fail test
        val blackBishop = Bishop(Color.BLACK, b5)
        b5.piece = blackBishop

        val cm = Checkmate(
            emptyBoard,
            whiteKing,
            mutableListOf(whiteKing, whiteRook, whiteRookTwo),
            blackKing,
            mutableListOf(blackKing, blackBishop)
        )

        println(emptyBoard.toString())
        println(cm.toString())

        assertTrue(cm.isBlackChecked())
        assertFalse(cm.isBlackCheckmated())
    }

    // https://lichess.org/editor/3k1R2/6R1/4n3/8/5K2/8/8/8_w_-_-_0_1?color=white
    @Test
    fun testTwoRooksCheckmateHasToCapture() {
        val emptyBoard = Board(false)
        val squares = emptyBoard.getSquares()

        val f4 = squares[Coordinate('f', 4)]!!
        assertNotNull(f4) // if square does not exist fail test
        val whiteKing = King(Color.WHITE, f4)
        f4.piece = whiteKing

        val g7 = squares[Coordinate('g', 7)]!!
        assertNotNull(g7) // if square does not exist fail test
        val whiteRook = Rook(Color.WHITE, g7)
        g7.piece = whiteRook

        val f8 = squares[Coordinate('f', 8)]!!
        assertNotNull(f8) // if square does not exist fail test
        val whiteRookTwo = Rook(Color.WHITE, f8)
        f8.piece = whiteRookTwo

        val d8 = squares[Coordinate('d', 8)]!!
        assertNotNull(d8) // if square does not exist fail test
        val blackKing = King(Color.BLACK, d8)
        d8.piece = blackKing

        val e6 = squares[Coordinate('e', 6)]!!
        assertNotNull(e6) // if square does not exist fail test
        val blackKnight = Knight(Color.BLACK, e6)
        e6.piece = blackKnight

        val cm = Checkmate(
            emptyBoard,
            whiteKing,
            mutableListOf(whiteKing, whiteRook, whiteRookTwo),
            blackKing,
            mutableListOf(blackKing, blackKnight)
        )

        println(emptyBoard.toString())
        println(cm.toString())

        assertTrue(cm.isBlackChecked())
        assertFalse(cm.isBlackCheckmated())
    }

    // https://lichess.org/editor/2k2R2/6R1/4n3/8/5KB1/8/8/8_w_-_-_0_1?color=white
    @Test
    fun testTwoRooksCheckmateCapturingCausesCheckmate() {
        val emptyBoard = Board(false)
        val squares = emptyBoard.getSquares()

        val f4 = squares[Coordinate('f', 4)]!!
        assertNotNull(f4) // if square does not exist fail test
        val whiteKing = King(Color.WHITE, f4)
        f4.piece = whiteKing

        val g7 = squares[Coordinate('g', 7)]!!
        assertNotNull(g7) // if square does not exist fail test
        val whiteRook = Rook(Color.WHITE, g7)
        g7.piece = whiteRook

        val f8 = squares[Coordinate('f', 8)]!!
        assertNotNull(f8) // if square does not exist fail test
        val whiteRookTwo = Rook(Color.WHITE, f8)
        f8.piece = whiteRookTwo

        val g4 = squares[Coordinate('g', 4)]!!
        assertNotNull(g4) // if square does not exist fail test
        val whiteBishop = Bishop(Color.WHITE, g4)
        g4.piece = whiteBishop

        val c8 = squares[Coordinate('c', 8)]!!
        assertNotNull(c8) // if square does not exist fail test
        val blackKing = King(Color.BLACK, c8)
        c8.piece = blackKing

        val e6 = squares[Coordinate('e', 6)]!!
        assertNotNull(e6) // if square does not exist fail test
        val blackKnight = Knight(Color.BLACK, e6)
        e6.piece = blackKnight

        val cm = Checkmate(
            emptyBoard,
            whiteKing,
            mutableListOf(whiteKing, whiteRook, whiteRookTwo, whiteBishop),
            blackKing,
            mutableListOf(blackKing, blackKnight)
        )

        println(emptyBoard.toString())
        println(cm.toString())

        assertTrue(cm.isBlackChecked())
        assertTrue(cm.isBlackCheckmated())
        assertTrue(cm.isWhiteChecked())
        assertFalse(cm.isWhiteCheckmated())
    }
}