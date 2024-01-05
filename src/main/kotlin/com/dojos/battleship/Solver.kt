package com.dojos.battleship

import com.dojos.battleship.Orientation.HORIZONTAL
import com.dojos.battleship.Orientation.VERTICAL
import com.dojos.battleship.ShotResult.HIT
import com.dojos.battleship.ShotResult.SUNK

private typealias Board = List<List<ShotResult?>>

class Solver(
  private val game: Game,
) {

  fun nextShot(): Position {
    val board = game.getBoard()
    val nextShot = board.getNextShot()

    game.shoot(nextShot.x, nextShot.y)

    return nextShot
  }

  private fun Board.getNextShot(): Position {
    forEachIndexed { y, row ->
      row.forEachIndexed { x, it ->
        if (it == HIT) {
          val orientation = findOrientation(x, y)
          when (orientation) {
            HORIZONTAL -> {
              if (isNotSet(x - 1, y)) return Position(x - 1, y)
              if (isNotSet(x + 1, y)) return Position(x + 1, y)
            }

            VERTICAL -> {
              if (isNotSet(x, y - 1)) return Position(x, y - 1)
              if (isNotSet(x, y + 1)) return Position(x, y + 1)
            }

            else -> {
              if (isNotSet(x - 1, y)) return Position(x - 1, y)
              if (isNotSet(x + 1, y)) return Position(x + 1, y)
              if (isNotSet(x, y - 1)) return Position(x, y - 1)
              if (isNotSet(x, y + 1)) return Position(x, y + 1)
            }
          }
        }
      }
    }

    forEachIndexed { y, row ->
      row.forEachIndexed { x, it ->
        if (it == null) {
          return Position(x, y)
        }
      }
    }

    throw IllegalStateException("No more shot to propose")
  }

  private fun Board.findOrientation(x: Int, y: Int): Orientation? {
    if (get(x - 1, y) in listOf(HIT, SUNK)) return HORIZONTAL
    if (get(x + 1, y) in listOf(HIT, SUNK)) return HORIZONTAL
    if (get(x, y - 1) in listOf(HIT, SUNK)) return VERTICAL
    if (get(x, y + 1) in listOf(HIT, SUNK)) return VERTICAL

    return null
  }

  private fun Board.isNotSet(x: Int, y: Int): Boolean {
    if (y !in indices) return false
    val row = this[y]
    if (x !in row.indices) return false
    return row[x] == null
  }

  private fun Board.get(x: Int, y: Int): ShotResult? {
    if (y !in indices) return null
    val row = this[y]
    if (x !in row.indices) return null
    return row[x]
  }
}