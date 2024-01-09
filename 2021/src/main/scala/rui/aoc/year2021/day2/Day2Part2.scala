package rui.aoc.year2021.day2

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day2Part2 extends ProblemSolution {
  override def solve(): AnyVal = FileIO
    .readResourceLines("day2.txt")
    .map(_.split(" ") match {
      case Array(instruction, value) => (instruction, value.toInt)
    })
    .foldLeft((0, 0, 0)) {
      case ((aim, horizontalPosition, depth), ("forward", value)) =>
        (aim, horizontalPosition + value, depth + value * aim)
      case ((aim, horizontalPosition, depth), ("down", value)) => (aim + value, horizontalPosition, depth)
      case ((aim, horizontalPosition, depth), ("up", value)) => (aim - value, horizontalPosition, depth)
    } match {
      case (_aim, horizontalPosition, depth) => horizontalPosition * depth
    }
}
