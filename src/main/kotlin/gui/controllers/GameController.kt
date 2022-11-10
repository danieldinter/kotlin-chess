package gg.dani.chess.gui.controllers

import gg.dani.chess.Game
import gg.dani.chess.IObserver
import gg.dani.chess.Player
import gg.dani.chess.board.Square
import gg.dani.chess.gui.BoardView
import gg.dani.chess.gui.MainView
import gg.dani.chess.gui.MessageView
import gg.dani.chess.gui.StatsView
import gg.dani.chess.pieces.Piece
import tornadofx.Controller
import kotlin.properties.Delegates
import kotlin.system.exitProcess

class GameController : Controller(), IObserver {

    private val mainView: MainView by inject()
    private val boardView: BoardView by inject()
    private val statsView: StatsView by inject()
    private val messageView: MessageView by inject()

    private var game: Game by Delegates.observable(Game()) { _, _, newValue ->
        boardView.board = newValue.board
        boardView.drawBoard()

        statsView.drawStats(game.playerOne, game.playerTwo, game.playersTurn)

        mainView.resize()
    }

    fun newGame() {
        game = Game()
    }

    fun quit() {
        exitProcess(0)
    }

    override fun update(winner: Player) {
        log.info("checkmate received in GameController")
        messageView.setMessage("Game over, winner: $winner")
        messageView.openModal()
    }

    fun getLegalMoves(piece: Piece): List<Square>? {
        return game.getLegalMoves(piece)
    }

    fun movePiece(piece: Piece?, targetSquare: Square) {
        if (piece == null)
            return

        game.movePiece(piece, targetSquare)
    }

}