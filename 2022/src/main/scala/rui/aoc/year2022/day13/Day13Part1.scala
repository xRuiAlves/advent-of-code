package rui.aoc.year2022.day13

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day13Part1 extends ProblemSolution {
  override def solve(): AnyVal = FileIO
    .readResourceLines("day13.txt")
    .filterNot(_.isBlank)
    .grouped(2)
    .map {
      case Array(left, right) => (
        PacketNode.fromStr(left)._1,
        PacketNode.fromStr(right)._1
      )
    }
    .zipWithIndex
    .filter(entry => entry._1._1.compareTo(entry._1._2) < 0)
    .toArray
    .map(_._2 + 1)
    .sum
}
