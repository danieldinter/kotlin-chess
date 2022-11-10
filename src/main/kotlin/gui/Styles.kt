package gg.dani.chess.gui

import tornadofx.Stylesheet
import tornadofx.c
import tornadofx.cssclass
import tornadofx.multi

class Styles : Stylesheet() {

    companion object {
        val mainView by cssclass()
        val boardView by cssclass()
        val statsView by cssclass()
    }

    init {
        mainView {
            backgroundColor = multi(c("#006400"))
        }
        boardView {
            backgroundColor = multi(c("#ffffcc"))
        }
        statsView {
            backgroundColor = multi(c("#1a1a1a"))
        }
    }

}