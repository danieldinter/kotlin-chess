package gg.dani.chess

import gg.dani.chess.helpers.Color

class Player(val name: String, val color: Color) {

    override fun toString(): String {
        return "${name}(${color})"
    }
}
