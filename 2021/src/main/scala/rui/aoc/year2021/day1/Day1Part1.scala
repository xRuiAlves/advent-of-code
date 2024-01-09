package rui.aoc.year2021.day1

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day1Part1 extends ProblemSolution {
  override def solve(): AnyVal = FileIO
    .readResourceLines("day1.txt")
    .map(_.toInt)
    .foldLeft((0, Int.MaxValue)) {
      case ((numIncreases, prev), curr) => (numIncreases + (if (curr > prev) 1 else 0), curr)
    }
    ._1
}
