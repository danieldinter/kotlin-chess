package gg.dani.chess

import gg.dani.chess.gui.MainView
import gg.dani.chess.gui.Styles
import mu.KotlinLogging
import tornadofx.App
import tornadofx.launch
import tornadofx.reloadStylesheetsOnFocus

val logger = KotlinLogging.logger {}

/**
 * Chess app GUI class
 *
 * @constructor Create empty Chess app
 */
class ChessApp : App(MainView::class, Styles::class) {
    init {
        logger.info { "ChessApp started" }
        reloadStylesheetsOnFocus()
    }
}

/**
 * Main method consuming command line arguments
 *
 * @TODO based on arguments given launch GUI or use command-line interface
 *
 * @param args
 */
fun main(args: Array<String>) {
    // if no args given just run the GUI
    if (args.isEmpty()) {
        launch<ChessApp>()
    }
}