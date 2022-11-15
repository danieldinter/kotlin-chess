package gg.dani.chess.gui

import gg.dani.chess.game.Player
import tornadofx.*

class StatsView : View() {

    override val root = vbox {
        addClass(Styles.statsView)
        spacing = 10.0
        padding = insets(10.0)
    }

    private fun drawPlayerInfo(playerOne: Player, playerTwo: Player) {
        root.add(PlayerLabel(playerOne.toString()))
        root.add(label("vs"))
        root.add(PlayerLabel(playerTwo.toString()))
    }

    private fun drawTurnInfo(playersTurn: Player) {
        root.add(label("turn:"))
        root.add(PlayerLabel(playersTurn.toString()))
    }

    fun drawStats(playerOne: Player, playerTwo: Player, playersTurn: Player) {
        root.children.removeAll()
        drawPlayerInfo(playerOne, playerTwo)
        drawTurnInfo(playersTurn)
    }

}