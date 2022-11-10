package gg.dani.chess

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Bishop
import gg.dani.chess.pieces.King
import gg.dani.chess.pieces.Piece
import gg.dani.chess.pieces.Queen
import java.util.concurrent.ConcurrentLinkedDeque
import kotlin.math.max
import kotlin.math.min


/**
 * Checks a board state for a check or a checkmate
 *
 * Based on https://github.com/jlundstedt/chess-java/blob/master/src/CheckmateDetector.java
 *
 * @property board the board state to be analyzed
 * @property whiteKing the white-colored king piece
 * @property whitePieces the white-colored pieces (including the king)
 * @property blackKing the black-colored king piece
 * @property blackPieces the black-colored pieces (including the king)
 * @constructor Create a new detector for check and checkmate
 */
class Checkmate(
    private val board: Board,
    private val whiteKing: King,
    private var whitePieces: MutableList<Piece>,
    private val blackKing: King,
    private var blackPieces: MutableList<Piece>
) {

    private lateinit var whiteMoves: HashMap<Square, MutableList<Piece>>
    private lateinit var blackMoves: HashMap<Square, MutableList<Piece>>
    private var movableSquares: MutableList<Square> = mutableListOf()

    init {
        initMoves()
        updateMoves()

        logger.debug { "checkmate is passed this board:\r\n$board" }
    }

    private fun initMoves() {
        whiteMoves = HashMap()
        blackMoves = HashMap()

        // get all board squares and initialize moves
        board.getSquares().values.forEach { square ->
            whiteMoves[square] = mutableListOf()
            blackMoves[square] = mutableListOf()
        }
    }

    private fun clearMoves() {
        whiteMoves.values.forEach { pieces ->
            pieces.clear()
        }
        blackMoves.values.forEach { pieces ->
            pieces.clear()
        }
    }

    private fun clearKingMovableSquares() {
        movableSquares.clear()
    }

    private fun updateMoves() {
        // clear movable pieces
        clearMoves()
        clearKingMovableSquares()

        // calculate moves by pieces available
        whitePieces.forEach { piece ->
            if (piece !is King) {
                val moves = piece.getAccessibleSquares(board)
                moves.forEach { square: Square ->
                    whiteMoves[square]?.add(piece)
                }
            }
        }

        blackPieces.forEach { piece ->
            if (piece !is King) {
                val moves = piece.getAccessibleSquares(board)
                moves.forEach { square: Square ->
                    blackMoves[square]?.add(piece)
                }
            }
        }
    }

    /**
     * Is white checked
     *
     * @return
     */
    fun isWhiteChecked(): Boolean {
        updateMoves()
        // check that current square of white king is not in black moves
        return if (blackMoves[whiteKing.currentPosition].isNullOrEmpty()) {
            movableSquares.addAll(board.getSquares().values)
            false
        } else {
            true
        }
    }

    /**
     * Is black checked
     *
     * @return
     */
    fun isBlackChecked(): Boolean {
        updateMoves()
        // check that current square of black king is not in white moves
        return if (whiteMoves[blackKing.currentPosition].isNullOrEmpty()) {
            movableSquares.addAll(board.getSquares().values)
            false
        } else {
            true
        }
    }

    private fun isChecked(king: King): Boolean {
        return if (king.color == Color.WHITE)
            isWhiteChecked()
        else
            isBlackChecked()
    }

    /**
     * Is white checkmated
     *
     * @return
     */
    fun isWhiteCheckmated(): Boolean {
        return isCheckmated(whiteMoves, blackMoves, whiteKing)
    }

    /**
     * Is black checkmated
     *
     * @return
     */
    fun isBlackCheckmated(): Boolean {
        return isCheckmated(blackMoves, whiteMoves, blackKing)
    }

    private fun isCheckmated(
        ownMoves: HashMap<Square, MutableList<Piece>>,
        opponentMoves: HashMap<Square, MutableList<Piece>>,
        ownKing: King
    ): Boolean {
        // if king is not even checked then abort immediately
        if (!isChecked(ownKing))
            return false

        // assume checkmate
        var checkmate = true

        // if king can evade the checkmate then no checkmate
        if (canEvadeCheckmate(opponentMoves, ownKing))
            checkmate = false

        // if threat can be captured by another figure then no checkmate
        val threats: MutableList<Piece> =
            opponentMoves[ownKing.currentPosition]!! // as the king is checked, cannot be empty
        if (canCapture(ownMoves, threats, ownKing))
            checkmate = false

        // if threat can be blocked by another figure then no checkmate
        if (canBlock(ownMoves, threats, ownKing))
            checkmate = false

        return checkmate
    }

    private fun canEvadeCheckmate(opponentMoves: HashMap<Square, MutableList<Piece>>, king: King): Boolean {
        var canEvade = false
        val kingsMoves: List<Square> = king.getAccessibleSquares(board)

        kingsMoves.forEach { square ->
            if (testMoveSuccess(king, square) && opponentMoves[square].isNullOrEmpty()) {
                movableSquares.add(square)
                canEvade = true
            }
        }

        return canEvade
    }

    private fun canCapture(
        ownMoves: HashMap<Square, MutableList<Piece>>,
        threats: MutableList<Piece>,
        king: King
    ): Boolean {
        var canCapture = false

        if (threats.size == 1) {
            val threatSquare = threats.last().currentPosition

            if (king.getAccessibleSquares(board).contains(threatSquare)) {
                movableSquares.add(threatSquare)
                if (testMoveSuccess(king, threatSquare))
                    canCapture = true
            }

            val caps = ownMoves[threatSquare]
            if (!caps.isNullOrEmpty()) {
                movableSquares.add(threatSquare)
                ConcurrentLinkedDeque(caps).forEach { piece ->
                    if (testMoveSuccess(piece, threatSquare))
                        canCapture = true
                }
            }

        }

        return canCapture
    }

    private fun canBlock(
        ownMoves: HashMap<Square, MutableList<Piece>>,
        threats: MutableList<Piece>,
        king: King
    ): Boolean {
        var canBlock = false

        if (threats.size == 1) {
            val threat = threats.last()
            val threatSquare = threat.currentPosition
            val kingsSquare = king.currentPosition

            if (kingsSquare.col == threatSquare.col) {
                val max = max(kingsSquare.row, threatSquare.row)
                val min = min(kingsSquare.row, threatSquare.row)

                for (i in min + 1 until max) {
                    ownMoves.forEach { (square, pieces) ->
                        if (square.row == i && square.col == kingsSquare.col && pieces.isNotEmpty()) {
                            movableSquares.add(square)
                            pieces.forEach { piece ->
                                if (testMoveSuccess(piece, square))
                                    canBlock = true
                            }
                        }
                    }
                }
            }

            if (kingsSquare.row == threatSquare.row) {
                val max = max(kingsSquare.col, threatSquare.col)
                val min = min(kingsSquare.col, threatSquare.col)

                for (i in min + 1 until max) {
                    ownMoves.forEach { (square, pieces) ->
                        if (square.col == i && square.row == kingsSquare.row && pieces.isNotEmpty()) {
                            movableSquares.add(square)
                            ConcurrentLinkedDeque(pieces).forEach { piece ->
                                if (testMoveSuccess(piece, square))
                                    canBlock = true
                            }
                        }
                    }
                }
            }

            if (threat is Queen || threat is Bishop) {
                val kingCol = kingsSquare.col
                val kingRow = kingsSquare.row
                val threatCol = threatSquare.col
                var threatRow = threatSquare.row

                if (kingCol > threatCol && kingRow > threatRow) {
                    for (i in threatCol + 1 until kingCol) {
                        threatRow++
                        ownMoves.forEach { (square, pieces) ->
                            if (square.row == threatRow && square.col == i && pieces.isNotEmpty()) {
                                movableSquares.add(square)
                                ConcurrentLinkedDeque(pieces).forEach { piece ->
                                    if (testMoveSuccess(piece, square))
                                        canBlock = true
                                }
                            }
                        }
                    }
                }

                if (kingCol > threatCol && threatRow > kingRow) {
                    for (i in threatCol + 1 until kingCol) {
                        threatRow--
                        ownMoves.forEach { (square, pieces) ->
                            if (square.row == threatRow && square.col == i && pieces.isNotEmpty()) {
                                movableSquares.add(square)
                                ConcurrentLinkedDeque(pieces).forEach { piece ->
                                    if (testMoveSuccess(piece, square))
                                        canBlock = true
                                }
                            }
                        }
                    }
                }

                if (threatCol > kingCol && kingRow > threatRow) {
                    for (i in threatCol - 1 downTo kingCol + 1) {
                        threatRow++
                        ownMoves.forEach { (square, pieces) ->
                            if (square.row == threatRow && square.col == i && pieces.isNotEmpty()) {
                                movableSquares.add(square)
                                ConcurrentLinkedDeque(pieces).forEach { piece ->
                                    if (testMoveSuccess(piece, square))
                                        canBlock = true
                                }
                            }
                        }
                    }
                }

                if (threatCol > kingCol && threatRow > kingRow) {
                    for (i in threatCol - 1 downTo kingCol + 1) {
                        threatRow--
                        ownMoves.forEach { (square, pieces) ->
                            if (square.row == threatRow && square.col == i && pieces.isNotEmpty()) {
                                movableSquares.add(square)
                                ConcurrentLinkedDeque(pieces).forEach { piece ->
                                    if (testMoveSuccess(piece, square))
                                        canBlock = true
                                }
                            }
                        }
                    }
                }
            }
        }

        return canBlock
    }

    /**
     * Test move success
     *
     * @param piece
     * @param to
     * @return
     */
    fun testMoveSuccess(piece: Piece, to: Square): Boolean {
        var isMoveSuccessful = true

        val initSquare = piece.currentPosition
        val initPiece = to.piece

        piece.move(to, true)

        // update whitePieces and blackPieces if one is eliminated by the tested move
        if (initPiece != null)
            if (initPiece.color == Color.WHITE)
                whitePieces.remove(initPiece)
            else
                whitePieces.remove(initPiece)

        updateMoves()

        if (piece.color == Color.BLACK && isBlackChecked())
            isMoveSuccessful = false

        if (piece.color == Color.WHITE && isWhiteChecked())
            isMoveSuccessful = false

        piece.move(initSquare, testMove = true, revert = true)
        if (initPiece != null) {
            to.piece = initPiece
            if (initPiece.color == Color.WHITE)
                whitePieces.add(initPiece)
            else
                blackPieces.add(initPiece)
        }

        updateMoves()

        movableSquares.add(to)

        return isMoveSuccessful
    }

    /**
     * Get movable squares
     *
     * @return
     */
    fun getMovableSquares(): MutableList<Square> {
        movableSquares.clear()
        if (isWhiteChecked()) {
            isWhiteCheckmated()
        } else if (isBlackChecked()) {
            isBlackCheckmated()
        }
        return movableSquares
    }

    override fun toString(): String {
        return "isWhiteChecked: ${isWhiteChecked()}, isWhiteCheckmated: ${isWhiteCheckmated()}, isBlackChecked: ${isBlackChecked()}, isBlackCheckmated: ${isBlackCheckmated()}"
    }

}