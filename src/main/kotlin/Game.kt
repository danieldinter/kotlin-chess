package gg.dani.chess

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.helpers.Color
import gg.dani.chess.pieces.Piece
import kotlin.random.Random

class Game : IObservable {
    var board = Board()
    val playerOne: Player
    val playerTwo: Player
    var playersTurn: Player
    override val observers = mutableListOf<IObserver>()

    init {
        val r = Random
        if (r.nextBoolean()) {
            playerOne = Player("player 1", Color.WHITE)
            playerTwo = Player("player 2", Color.BLACK)
            playersTurn = playerOne
        } else {
            playerOne = Player("player 1", Color.BLACK)
            playerTwo = Player("player 2", Color.WHITE)
            playersTurn = playerTwo
        }
    }

    private fun getPlayerByColor(color: Color): Player {
        return if (playerOne.color == color)
            playerOne
        else
            playerTwo
    }

    private fun switchPlayerTurns() {
        playersTurn = if (playersTurn == playerOne)
            playerTwo
        else
            playerOne
    }

    fun getLegalMoves(piece: Piece): List<Square>? {
        return if (playersTurn.color == piece.color)
            piece.getAccessibleSquares(board)
        else
            null
    }

    fun movePiece(piece: Piece, to: Square) {
        if (piece == null)
            return

        if (piece.color != playersTurn.color)
            return

        val checkmate = board.cm
        val legalMoves = piece.getAccessibleSquares(board)
        val movable = checkmate.getMovableSquares()

        logger.info { checkmate }

        logger.info {
            "legalMoves.contains(to): ${legalMoves.contains(to)}, movable.contains(to): ${movable.contains(to)}, checkmate.testMoveSuccess(piece, to): ${
                checkmate.testMoveSuccess(
                    piece,
                    to
                )
            }"
        }

        if (legalMoves.contains(to) && movable.contains(to)
            && checkmate.testMoveSuccess(piece, to)
        ) {
            piece.move(to)

            if (checkmate.isBlackCheckmated()) {
                sendCheckmateEvent(getPlayerByColor(Color.WHITE))
            } else if (checkmate.isWhiteCheckmated()) {
                sendCheckmateEvent(getPlayerByColor(Color.BLACK))
            } else {
                switchPlayerTurns()
            }
        }
    }

    override fun toString(): String {
        return "players: [$playerOne, $playerTwo]\r\n$board"
    }
}