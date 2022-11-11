package gg.dani.chess.game

import gg.dani.chess.helpers.Color

/**
 * Player object representing the two players of the game.
 *
 * @property name Player's name
 * @property color Color of the player
 * @constructor Create a new Player
 */
class Player(val name: String, val color: Color) {

    override fun toString(): String {
        return "${name}(${color})"
    }
}
