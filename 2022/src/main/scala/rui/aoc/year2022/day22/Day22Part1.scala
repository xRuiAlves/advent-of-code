package rui.aoc.year2022.day22

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day17.Day17._
import rui.aoc.year2022.day22.Day22.{MonkeyMap, computePassword, computePasswordTotalValue, readInstructions}
import rui.aoc.year2022.utils.FileIO

class Day22Part1 extends ProblemSolution {
  override def solve(): AnyVal = computePassword(
    FileIO.readResourceLines("day22.txt"),
    this.move
  )

  def move(
    map: MonkeyMap,
    movementValue: Int,
    state: State
  ): State = if (movementValue == 0) state else state.dir match {
    // Right
    case 0 =>
      val followingX = (state.x + 1 ) % map(state.y).length
      move(map, movementValue - 1, State(
        map(state.y)(followingX) match {
          case '.' => followingX
          case '#' => state.x
          case ' ' => {
            val nextXIndex = getFirstNonBlankAtRowStart(map, state.y)
            map(state.y)(nextXIndex) match {
              case '.' => nextXIndex
              case '#' => state.x
            }
          }
        },
        state.y,
        state.dir
      ))
    // Down
    case 1 =>
      val followingY = (state.y + 1) % map.length
      move(map, movementValue - 1, State(
        state.x,
        map(followingY)(state.x) match {
          case '.' => followingY
          case '#' => state.y
          case ' ' => {
            val nextYIndex = getFirstNonBlankAtColStart(map, state.x)
            map(nextYIndex)(state.x) match {
              case '.' => nextYIndex
              case '#' => state.y
            }
          }
        },
        state.dir
      ))
    // Left
    case 2 =>
      val previousX =
        if (state.x == 0) map(state.y).length - 1
        else state.x - 1
      move(map, movementValue - 1, State(
        map(state.y)(previousX) match {
          case '.' => previousX
          case '#' => state.x
          case ' ' => {
            val nextXIndex = getFirstNonBlankAtRowEnd(map, state.y)
            map(state.y)(nextXIndex) match {
              case '.' => nextXIndex
              case '#' => state.x
            }
          }
        },
        state.y,
        state.dir
      ))
    // Up
    case 3 =>
      val previousY =
        if (state.y == 0) map.length - 1
        else state.y - 1
      move(map, movementValue - 1, State(
        state.x,
        map(previousY)(state.x) match {
          case '.' => previousY
          case '#' => state.y
          case ' ' => {
            val nextYIndex = getFirstNonBlankAtColEnd(map, state.x)
            map(nextYIndex)(state.x) match {
              case '.' => nextYIndex
              case '#' => state.y
            }
          }
        },
        state.dir
      ))
  }

  def getFirstNonBlankAtRowStart(map: MonkeyMap, y: Int): Int =
    map(y).zipWithIndex.find(_._1 != ' ').get._2

  def getFirstNonBlankAtRowEnd(map: MonkeyMap, y: Int): Int =
    map(y).zipWithIndex.findLast(_._1 != ' ').get._2

  def getFirstNonBlankAtColStart(map: MonkeyMap, x: Int): Int = {
    map.indices.foreach(i => {
      if (map(i)(x) != ' ') return i
    })
    throw new Exception("Not found")
  }

  def getFirstNonBlankAtColEnd(map: MonkeyMap, x: Int): Int = {
    map.indices.reverse.foreach(i => {
      if (map(i)(x) != ' ') return i
    })
    throw new Exception("Not found")
  }
}