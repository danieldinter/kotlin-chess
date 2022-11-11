package gg.dani.chess.game

interface IObservable {
    val observers: MutableList<IObserver>

    fun add(observer: IObserver) {
        observers.add(observer)
    }

    fun remove(observer: IObserver) {
        observers.remove(observer)
    }

    fun sendCheckmateEvent(winner: Player) {
        observers.forEach { it.update(winner) }
    }
}