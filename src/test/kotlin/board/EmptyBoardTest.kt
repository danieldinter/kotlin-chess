package board

import gg.dani.chess.board.Board
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class EmptyBoardTest: StringSpec({

    val emptyBoard = Board(false)

    "black pieces should be empty" {
        emptyBoard.blackPieces.shouldBeEmpty()
    }

    "white pieces should be empty" {
        emptyBoard.whitePieces.shouldBeEmpty()
    }

    "no square should contain a piece" {
        emptyBoard.getSquares().values.forEach { square ->
            square.piece shouldBe null
            square.occupied shouldBe false
        }
    }

})