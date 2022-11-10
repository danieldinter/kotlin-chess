package gg.dani.chess.gui

import tornadofx.View
import tornadofx.addClass
import tornadofx.borderpane

class MainView : View("Kotlin Chess by dani") {

    override val root = borderpane {
        top<MenuView>()
        left<StatsView>()
        center<BoardView>()

        addClass(Styles.mainView)

        setMinSize(200.0, 200.0)
    }

    fun resize() {
        //@TODO Calculate sizes from content
        primaryStage.width = 1160.0
        primaryStage.height = 963.0
    }

}