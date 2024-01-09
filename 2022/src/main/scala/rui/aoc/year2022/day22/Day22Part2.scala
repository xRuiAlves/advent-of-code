package rui.aoc.year2022.day22

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day22.Day22.{MonkeyMap, computePassword, computePasswordTotalValue, readInstructions}
import rui.aoc.year2022.utils.FileIO

class Day22Part2 extends ProblemSolution {
  private final val CUBE_SIZE = 50

  override def solve(): AnyVal = computePassword(
    FileIO.readResourceLines("day22.txt"),
    this.move
  )

  def move(
    map: MonkeyMap,
    movementValue: Int,
    state: State
  ): State = if (movementValue == 0) state else {
    val afterMoveState = moveUnitForwardInCube(state)
    if (map(afterMoveState.y)(afterMoveState.x) == '.')
      move(map, movementValue - 1, afterMoveState)
    else
      state
  }

  def moveUnitForwardInCube(state: State): State = {
    val currCubeId = cubeId(state.x, state.y)
    (currCubeId, state.dir) match {
      case (0, 0) => State(state.x + 1, state.y, state.dir)
      case (0, 1) => State(state.x, state.y + 1, state.dir)
      case (0, 2) =>
        if (state.x - 1 >= CUBE_SIZE) State(state.x - 1, state.y, state.dir)
        else State(0, 3*CUBE_SIZE - state.y - 1, 0)
      case (0, 3) =>
        if (state.y - 1 >= 0) State(state.x, state.y - 1, state.dir)
        else State(0, 3*CUBE_SIZE + (state.x - CUBE_SIZE), 0)

      case (1, 0) =>
        if (state.x + 1 < 3*CUBE_SIZE) State(state.x + 1, state.y, state.dir)
        else State(2*CUBE_SIZE - 1, 3*CUBE_SIZE - 1 - state.y, 2)
      case (1, 1) =>
        if (state.y + 1 < CUBE_SIZE) State(state.x, state.y + 1, state.dir)
        else State(2*CUBE_SIZE - 1, CUBE_SIZE + (state.x - 2*CUBE_SIZE), 2)
      case (1, 2) => State(state.x - 1, state.y, state.dir)
      case (1, 3) =>
        if (state.y - 1 >= 0) State(state.x, state.y - 1, state.dir)
        else State(state.x - 2*CUBE_SIZE, 4*CUBE_SIZE - 1, state.dir)

      case (2, 0) =>
        if (state.x + 1 < 2*CUBE_SIZE) State(state.x + 1, state.y, state.dir)
        else State(2*CUBE_SIZE + (state.y - CUBE_SIZE), CUBE_SIZE - 1, 3)
      case (2, 1) => State(state.x, state.y + 1, state.dir)
      case (2, 2) =>
        if (state.x - 1 >= CUBE_SIZE) State(state.x - 1, state.y, state.dir)
        else State(state.y - CUBE_SIZE, 2*CUBE_SIZE, 1)
      case (2, 3) => State(state.x, state.y - 1, state.dir)

      case (3, 0) =>
        if (state.x + 1 < 2*CUBE_SIZE) State(state.x + 1, state.y, state.dir)
        else State(3*CUBE_SIZE - 1, CUBE_SIZE - (state.y - 2*CUBE_SIZE) - 1, 2)
      case (3, 1) =>
        if (state.y + 1 < 3*CUBE_SIZE) State(state.x, state.y + 1, state.dir)
        else State(CUBE_SIZE - 1, 3*CUBE_SIZE + state.x - CUBE_SIZE, 2)
      case (3, 2) => State(state.x - 1, state.y, state.dir)
      case (3, 3) => State(state.x, state.y - 1, state.dir)

      case (4, 0) => State(state.x + 1, state.y, state.dir)
      case (4, 1) => State(state.x, state.y + 1, state.dir)
      case (4, 2) =>
        if (state.x - 1 >= 0) State(state.x - 1, state.y, state.dir)
        else State(CUBE_SIZE, CUBE_SIZE - (state.y - 2*CUBE_SIZE) - 1, 0)
      case (4, 3) =>
        if (state.y - 1 >= 2*CUBE_SIZE) State(state.x, state.y - 1, state.dir)
        else State(CUBE_SIZE, CUBE_SIZE + state.x, 0)

      case (5, 0) =>
        if (state.x + 1 < CUBE_SIZE) State(state.x + 1, state.y, state.dir)
        else State(CUBE_SIZE + (state.y - 3*CUBE_SIZE), 3*CUBE_SIZE - 1, 3)
      case (5, 1) =>
        if (state.y + 1 < 4*CUBE_SIZE) State(state.x, state.y + 1, state.dir)
        else State(state.x + 2*CUBE_SIZE, 0, state.dir)
      case (5, 2) =>
        if (state.x - 1 >= 0) State(state.x - 1, state.y, state.dir)
        else State((state.y - 3*CUBE_SIZE) + CUBE_SIZE, 0, 1)
      case (5, 3) => State(state.x, state.y - 1, state.dir)
    }
  }

  def cubeId(x: Int, y: Int): Int = {
    if (y < CUBE_SIZE && x >= CUBE_SIZE && x < 2 * CUBE_SIZE) 0
    else if (y < CUBE_SIZE && x >= 2 * CUBE_SIZE) 1
    else if (y >= CUBE_SIZE && y < 2 * CUBE_SIZE && x >= CUBE_SIZE && x < 2 * CUBE_SIZE) 2
    else if (y >= 2 * CUBE_SIZE && y < 3 * CUBE_SIZE && x >= CUBE_SIZE && x < 2 * CUBE_SIZE) 3
    else if (y >= 2 * CUBE_SIZE && y < 3 * CUBE_SIZE && x < CUBE_SIZE) 4
    else 5
  }
}