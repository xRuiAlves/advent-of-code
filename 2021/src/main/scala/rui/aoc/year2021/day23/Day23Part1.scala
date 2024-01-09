package rui.aoc.year2021.day23

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day23Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val input = Day23.parseInput(FileIO.readResourceLines("day23.txt"))
    val board = Day23.parseBoard(input)
    val target = Day23.targetHash(input.length, Day23.Part1RoomSize)

    Day23.solve(board)(target)
  }
}
