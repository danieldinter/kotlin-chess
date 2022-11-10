package gg.dani.chess.gui

import gg.dani.chess.board.Board
import gg.dani.chess.board.Square
import gg.dani.chess.gui.controllers.GameController
import gg.dani.chess.logger
import javafx.scene.layout.GridPane
import tornadofx.View
import tornadofx.addClass
import tornadofx.gridpane

class BoardView : View() {
    private val gameController: GameController by inject()

    private var activeSquare: Square? = null
    var board: Board = Board()

    override val root = gridpane {
        addClass(Styles.boardView)
    }

    init {
        subscribe<SquareSelectEvent> { squareSelectEvent ->
            changeActiveSquare(squareSelectEvent.square)
        }
    }

    fun drawBoard() {
        root.children.removeAll()
        board.getSquares().forEach { square ->
            val sp = SquarePane(square.value, gameController)

            sp.setOnMouseClicked { fire(SquareSelectEvent(square.value)) }
            sp.imageview.setOnMouseClicked { fire(SquareSelectEvent(square.value)) }

            addBorder(root)
            addRespectBorder(root, sp, square.key.col.value, Board.ROWS - square.key.row.value + 1)
        }
    }

    private fun addBorder(root: GridPane) {
        for (col in 1..Board.COLS) {
            root.add(ColLabel(col), col, 0)
            root.add(ColLabel(col), col, Board.ROWS + 1)
        }
        for (row in 1..Board.ROWS) {
            root.add(RowLabel(row), 0, row)
            root.add(RowLabel(row), Board.COLS + 1, row)
        }
    }

    private fun addRespectBorder(root: GridPane, sp: SquarePane, col: Int, row: Int) {
        root.add(sp, col, row)
    }

    private fun changeActiveSquare(square: Square?) {
        if (square != null) {
            activeSquare = square
            val accessibleSquares = square.piece?.let { gameController.getLegalMoves(it) }

            logger.debug("accessibleSquares: $accessibleSquares")

            root.children.forEach { node ->
                val squarePane = (node as? SquarePane)

                squarePane?.changeColorNormal()
                squarePane?.toggleDot(false)

                if (squarePane?.square?.equals(activeSquare) == true)
                    squarePane.changeColorActive()

                if (accessibleSquares?.contains(squarePane?.square) == true)
                //squarePane?.changeColorAccessible()
                    squarePane?.toggleDot(true)
            }
        } else {
            root.children.forEach { node ->
                val squarePane = (node as? SquarePane)

                squarePane?.changeColorNormal()
                squarePane?.toggleDot(false)
            }
        }
    }

}