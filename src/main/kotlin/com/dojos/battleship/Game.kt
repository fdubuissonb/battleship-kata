package com.dojos.battleship

class Game(width: Int, height: Int, init: List<Ship>) {

  fun shoot(x: Int, y: Int): ShotResult {
    TODO()
  }

  fun getBoard(): List<List<ShotResult?>> {
    TODO()
  }

  fun isOver(): Boolean {
    TODO()
  }
}

sealed class Ship(val size: Int, open val position: Position, open val orientation: Orientation) {
  class Carrier(override val position: Position, override val orientation: Orientation) : Ship(5, position, orientation)
  class Battleship(override val position: Position, override val orientation: Orientation) : Ship(4, position, orientation)
  class Cruiser(override val position: Position, override val orientation: Orientation) : Ship(3, position, orientation)
  class Submarine(override val position: Position, override val orientation: Orientation) : Ship(3, position, orientation)
  class Destroyer(override val position: Position, override val orientation: Orientation) : Ship(2, position, orientation)
}

data class Position(val x: Int, val y: Int)

enum class Orientation {
  HORIZONTAL, VERTICAL
}

enum class ShotResult {
  MISS, HIT, SUNK
}
