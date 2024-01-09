package rui.aoc.year2022.day1

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day1Part2 extends ProblemSolution {
  case class Status(list: List[Int], currSum: Int)

  override def solve(): AnyVal = FileIO
    .readResourceLines("day1.txt")
    .foldLeft(Status(List.empty, 0))((status: Status, curr) => curr match {
      case "" => Status(status.list :+ status.currSum, 0)
      case _  => Status(status.list, status.currSum + curr.toInt)
    })
    .list
    .sorted
    .reverse
    .take(3)
    .sum
}
