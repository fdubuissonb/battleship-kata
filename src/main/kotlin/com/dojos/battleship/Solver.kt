package com.dojos.battleship

class Solver(
  private val game: Game,
) {

  fun nextShot(): Position {
    return Position(0, 0)
  }
}