package rui.aoc.year2022.day10

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day10.Day10.computeCycleValues
import rui.aoc.year2022.utils.FileIO

class Day10Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val cycleValues = computeCycleValues(FileIO.readResourceLines("day10.txt"))

    (20 to  cycleValues.length by 40)
      .map(i => i * cycleValues(i - 1))
      .sum
  }
}
