package gg.dani.chess.gui

import gg.dani.chess.board.Square
import gg.dani.chess.gui.controllers.GameController
import gg.dani.chess.logger
import gg.dani.chess.pieces.Piece
import javafx.scene.image.ImageView
import javafx.scene.input.ClipboardContent
import javafx.scene.input.DragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.FX
import tornadofx.add
import tornadofx.circle

class SquarePane(val square: Square, val gameController: GameController) : StackPane() {

    private val s = 100.0 // side of rectangle
    private val rec = Rectangle(s, s, s, s)
    private val row = square.coordinate.row.value
    private val col = square.coordinate.col.value
    private val dot = circle {
        radius = 10.0
        fill = Color.LIGHTBLUE
    }

    val piece: Piece? get() = square.piece
    var imageview = ImageView()

    init {
        changeColorNormal()

        this.setOnDragDetected { event -> onDragDetected(event) }
        this.setOnDragOver { event -> onDragOver(event) }
        this.setOnDragDropped { event -> onDragDropped(event) }
        this.setOnDragDone { event -> onDragDone(event) }

        this.add(rec)

        drawPiece()
    }

    private fun drawPiece() {
        logger.debug { "drawPiece() called - square: $square; piece: $piece" }
        if (piece != null) {
            this.children.remove(imageview)
            val cssShadow = "-fx-effect: dropshadow(three-pass-box, rgba(255,255,255,1), 1, 0, 0, 0);"
            imageview = ImageView("pieces/" + piece!!.getImageName())
            imageview.style = cssShadow

            // add events to imageview
            imageview.setOnDragDetected { event -> onDragDetected(event) }
            imageview.setOnDragOver { event -> onDragOver(event) }
            imageview.setOnDragDropped { event -> onDragDropped(event) }
            imageview.setOnDragDone { event -> onDragDone(event) }

            this.add(imageview)
        } else {
            this.children.remove(imageview)
        }
        FX.eventbus.fire(SquareSelectEvent(null))
    }

    fun toggleDot(setDot: Boolean) {
        if (setDot)
            this.add(dot)
        else
            this.children.remove(dot)
    }

    fun changeColorNormal() {
        if ((row + col) % 2 == 0) {
            rec.fill = Color.WHITE
        } else {
            rec.fill = Color.BLACK
        }
    }

    fun changeColorActive() {
        rec.fill = Color.LIGHTBLUE
    }

    fun changeColorAccessible() {
        rec.fill = Color.PALEVIOLETRED
    }

    private fun onDragDetected(event: MouseEvent) {
        if (piece == null) {
            event.consume()
        } else {
            val dragboard = imageview.startDragAndDrop(TransferMode.MOVE)
            val clipboardContent = ClipboardContent()
            clipboardContent.putImage(imageview.image)
            dragboard.setContent(clipboardContent)
            event.consume()
        }
    }

    private fun onDragOver(event: DragEvent) {
        val sourceSquarePane = getSourceSquarePane(event)
        val legalMoves = sourceSquarePane.piece?.let { gameController.getLegalMoves(it) }

        if (legalMoves != null) {
            if (legalMoves.contains(square) && event.dragboard.hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE)
            }
        }

        event.consume()
    }

    private fun onDragDropped(event: DragEvent) {
        val dragboard = event.dragboard
        var success = false

        val targetSquare = square
        val sourceSquarePane = getSourceSquarePane(event)

        if (dragboard.hasImage()) {
            gameController.movePiece(sourceSquarePane.piece, targetSquare)
            this.drawPiece()
            success = true
        }

        event.isDropCompleted = success
        event.consume()
    }

    private fun onDragDone(event: DragEvent) {
        if (event.transferMode === TransferMode.MOVE) {
            val sourceSquarePane = getSourceSquarePane(event)
            sourceSquarePane.drawPiece()
        }
        event.consume()
    }

    private fun getSourceSquarePane(event: DragEvent): SquarePane {
        // check origin of event: either SquarePane or child ImageView
        val sourceSquarePane: SquarePane = when (val source = event.gestureSource) {
            is SquarePane -> {
                source
            }

            is ImageView -> {
                source.parent as SquarePane
            }

            else -> {
                throw Exception("something wrong")
            }
        }
        return sourceSquarePane
    }

}