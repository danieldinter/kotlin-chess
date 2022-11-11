package gg.dani.chess.game

interface IObserver {
    fun update(winner: Player)
}