package rui.aoc.year2021.day23

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day23Part2 extends ProblemSolution {
  private[this] final val RoomExtras = Map(
    2 -> "DD",
    4 -> "CB",
    6 -> "BA",
    8 -> "AC"
  )

  override def solve(): AnyVal = {
    val input = Day23.parseInput(FileIO.readResourceLines("day23.txt"))
    val board = Day23.parseBoard(input).zipWithIndex.map {
      case (cell, idx) if cell.length == Day23.Part1RoomSize => s"${cell.head}${RoomExtras(idx)}${cell.last}"
      case (cell, _) => cell
    }
    val target = Day23.targetHash(input.length, Day23.Part2RoomSize)
    Day23.solve(board)(target)
  }
}
