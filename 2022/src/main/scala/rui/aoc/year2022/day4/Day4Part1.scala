package rui.aoc.year2022.day4

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO
class Day4Part1 extends ProblemSolution {
  override def solve(): AnyVal = FileIO
    .readResourceLines("day4.txt")
    .map(_.split(","))
    .map(_.map(Pair.fromStr))
    .map {
      case Array(p1, p2) =>
        if ((p1 contains p2) || (p2 contains p1)) 1
        else 0
    }
    .sum
}
