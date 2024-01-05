package com.dojos.battleship

import com.dojos.battleship.Orientation.HORIZONTAL
import com.dojos.battleship.Orientation.VERTICAL
import com.dojos.battleship.Ship.Carrier
import com.dojos.battleship.Ship.Cruiser
import com.dojos.battleship.Ship.Destroyer
import com.dojos.battleship.ShotResult.HIT
import com.dojos.battleship.ShotResult.MISS
import com.dojos.battleship.ShotResult.SUNK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameTest {

  @Test
  fun `should shoot and miss`() {
    val ships = listOf(
      Carrier(Position(0, 0), HORIZONTAL),
      Cruiser(Position(2, 5), VERTICAL),
    )
    val game = Game(10, 10, ships)

    val result = game.shoot(0, 1)

    assertEquals(MISS, result)
  }

  @Test
  fun `should shoot and hit`() {
    val ships = listOf(
      Carrier(Position(0, 0), HORIZONTAL),
      Cruiser(Position(2, 5), VERTICAL),
    )
    val game = Game(10, 10, ships)

    val result = game.shoot(2, 6)

    assertEquals(HIT, result)
  }

  @Test
  fun `should shoot and sunk`() {
    val ships = listOf(
      Carrier(Position(0, 0), HORIZONTAL),
      Destroyer(Position(2, 5), VERTICAL),
    )
    val game = Game(10, 10, ships)

    game.shoot(2, 5)
    val result = game.shoot(2, 6)

    assertEquals(SUNK, result)
  }

  @Test
  fun `should be over when all ships are sunk`() {
    val ships = listOf(
      Carrier(Position(0, 0), HORIZONTAL),
      Destroyer(Position(2, 5), VERTICAL),
    )
    val game = Game(10, 10, ships)

    for (i in 5..6) {
      game.shoot(2, i)
    }
    for (i in 0..4) {
      game.shoot(i, 0)
    }

    assertTrue(game.isOver())
  }

  @Test
  fun `should get the board cells`() {
    val ships = listOf(
      Destroyer(Position(0, 0), HORIZONTAL),
      Destroyer(Position(2, 1), VERTICAL),
    )
    val game = Game(4, 4, ships)

    game.shoot(0, 0)
    game.shoot(1, 0)
    game.shoot(2, 0)
    game.shoot(2, 3)

    val board = game.getBoard()
    assertEquals(
      listOf(
        listOf<ShotResult?>(HIT, SUNK, MISS, null),
        listOf<ShotResult?>(null, null, null, null),
        listOf<ShotResult?>(null, null, null, null),
        listOf<ShotResult?>(null, null, MISS, null),
      ),
      board
    )
  }
}
