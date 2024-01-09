package rui.aoc.year2022.day1

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day1Part1 extends ProblemSolution {
  case class Status(max: Int, currSum: Int)

  override def solve(): AnyVal = FileIO
    .readResourceLines("day1.txt")
    .foldLeft(Status(0, 0))((status: Status, curr) => curr match {
      case "" => Status(math.max(status.max, status.currSum), 0)
      case _  => Status(status.max, status.currSum + curr.toInt)
    })
    .max
}
