package gg.dani.chess.gui

import gg.dani.chess.gui.controllers.GameController
import tornadofx.*

class MenuView : View() {

    private val game: GameController by inject()

    override val root = menubar {
        menu("File") {
            item("New Game", "Shortcut+N").action {
                game.newGame()
            }
            item("Quit", "Shortcut+Q").action {
                game.quit()
            }
        }
    }

}