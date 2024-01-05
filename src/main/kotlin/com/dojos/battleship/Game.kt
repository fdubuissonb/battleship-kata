package com.dojos.battleship

import com.dojos.battleship.Orientation.HORIZONTAL
import com.dojos.battleship.Orientation.VERTICAL
import com.dojos.battleship.ShotResult.HIT
import com.dojos.battleship.ShotResult.MISS
import com.dojos.battleship.ShotResult.SUNK

class Game(width: Int, height: Int, init: List<Ship>) {
  private val ships = init
  private val cells = List(height) { MutableList<ShotResult?>(width) { null } }

  fun shoot(x: Int, y: Int): ShotResult {

    val shotResult = ships.firstNotNullOfOrNull { ship ->
      when (ship.shot(x, y)) {
        HIT -> HIT
        SUNK -> SUNK
        MISS -> null
      }
    } ?: MISS

    cells[y][x] = shotResult
    return shotResult
  }

  fun getBoard(): List<List<ShotResult?>> {
    return cells
  }

  fun isOver(): Boolean {
    return ships.all { it.isSunk() }
  }
}

sealed class Ship(val size: Int, open val position: Position, open val orientation: Orientation) {
  class Carrier(override val position: Position, override val orientation: Orientation) : Ship(5, position, orientation)
  class Battleship(override val position: Position, override val orientation: Orientation) : Ship(4, position, orientation)
  class Cruiser(override val position: Position, override val orientation: Orientation) : Ship(3, position, orientation)
  class Submarine(override val position: Position, override val orientation: Orientation) : Ship(3, position, orientation)
  class Destroyer(override val position: Position, override val orientation: Orientation) : Ship(2, position, orientation)


  private val hits = BooleanArray(size)

  fun shot(x: Int, y: Int): ShotResult {
    when (orientation) {
      HORIZONTAL -> {
        if (y != position.y) return MISS
        if (x !in position.x..<position.x + size) return MISS
        hits[x - position.x] = true
        return if (hits.all { it }) SUNK else HIT
      }

      VERTICAL -> {
        if (x != position.x) return MISS
        if (y !in position.y..<position.y + size) return MISS
        hits[y - position.y] = true
        return if (hits.all { it }) SUNK else HIT
      }
    }
  }

  fun isSunk(): Boolean {
    return hits.all { it }
  }
}

data class Position(val x: Int, val y: Int)

enum class Orientation {
  HORIZONTAL, VERTICAL
}

enum class ShotResult {
  MISS, HIT, SUNK
}
