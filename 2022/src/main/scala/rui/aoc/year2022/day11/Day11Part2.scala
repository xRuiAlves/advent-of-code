package rui.aoc.year2022.day11

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day11.Day11.{parseMonkeys, processRounds}
import rui.aoc.year2022.utils.FileIO

class Day11Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val monkeys = parseMonkeys(FileIO.readResourceLines("day11.txt"))
    val divisorsLcm = monkeys
      .map(_.divTestDelta)
      .product
    def modifyWorryLevel(curr: Long): Long = curr % divisorsLcm

    processRounds(monkeys, 10000, modifyWorryLevel)
  }
}
