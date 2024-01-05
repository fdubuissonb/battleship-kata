package com.dojos.battleship

import com.dojos.battleship.Orientation.HORIZONTAL
import com.dojos.battleship.Orientation.VERTICAL
import com.dojos.battleship.Ship.Battleship
import com.dojos.battleship.Ship.Carrier
import com.dojos.battleship.Ship.Cruiser
import com.dojos.battleship.Ship.Destroyer
import com.dojos.battleship.Ship.Submarine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SolverTest {
  @Test
  fun `should not shoot twice at the same position`() {
    val ships = listOf(
      Destroyer(Position(0, 0), HORIZONTAL),
    )
    val game = Game(2, 2, ships)
    game.shoot(0, 0)
    game.shoot(0, 1)
    game.shoot(1, 1)

    val solver = Solver(game)
    val nextShot = solver.nextShot()

    assertEquals(Position(1, 0), nextShot)
  }

  @Test
  fun `should propose shot next to hit ship`() {
    val ships = listOf(
      Carrier(Position(0, 0), HORIZONTAL),
      Destroyer(Position(2, 1), VERTICAL),
    )
    val game = Game(10, 10, ships)
    game.shoot(2, 1)

    val solver = Solver(game)
    val nextShot = solver.nextShot()

    assert(nextShot in listOf(Position(2, 0), Position(2, 2), Position(1, 1), Position(3, 1)))
  }

  @Test
  fun `should propose best shot when ship is half sunk`() {
    val ships = listOf(
      Carrier(Position(1, 1), HORIZONTAL),
      Submarine(Position(2, 5), VERTICAL),
    )
    val game = Game(10, 10, ships)
    game.shoot(2, 4)
    game.shoot(2, 5)
    game.shoot(2, 6)

    val solver = Solver(game)
    val nextShot = solver.nextShot()

    assertEquals(Position(2, 7), nextShot)
  }

  @Test
  fun `should win in max 10 shots`() {
    /*
      ....
      .dd.
      ....
      ....
    */
    val ships = listOf(
      Destroyer(Position(1, 1), VERTICAL),
    )
    val game = Game(4, 4, ships)
    val solver = Solver(game)
    for (i in 1..10) {
      println(solver.nextShot())

      if (game.isOver()) {
        println("Exiting after $i shots")
        break
      }
    }

    assertTrue(game.isOver())
  }

  @Test
  fun `should win in max 15 shots`() {
    /*
      dd..
      ...c
      ...c
      ...c
    */
    val ships = listOf(
      Destroyer(Position(0, 0), HORIZONTAL),
      Cruiser(Position(3, 1), VERTICAL),
    )
    val game = Game(4, 4, ships)
    val solver = Solver(game)
    for (i in 1..12) {
      solver.nextShot()

      if (game.isOver()) {
        println("Exiting after $i shots")
        break
      }
    }

    assertTrue(game.isOver())
  }

  @Test
  fun `should win with the minimum shot count`() {
    /*
      .dd.....a.
      ........a.
      ...c....a.
      ...c....a.
      ...c....a.
      ......b...
      ......b...
      ......b...
      ......b...
      .sss......
    */
    val ships = listOf(
      Destroyer(Position(1, 1), HORIZONTAL),
      Submarine(Position(1, 9), HORIZONTAL),
      Cruiser(Position(3, 2), VERTICAL),
      Battleship(Position(6, 5), VERTICAL),
      Carrier(Position(8, 0), VERTICAL),
    )
    val game = Game(10, 10, ships)
    val solver = Solver(game)
    for (i in 1..1000) {
      solver.nextShot()

      if (game.isOver()) {
        println("Exiting after $i shots")
        break
      }
    }

    assertTrue(game.isOver())
  }
}