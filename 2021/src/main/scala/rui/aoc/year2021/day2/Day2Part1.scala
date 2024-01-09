package rui.aoc.year2021.day2

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day2Part1 extends ProblemSolution {
  override def solve(): AnyVal = FileIO
    .readResourceLines("day2.txt")
    .map(_.split(" ") match {
      case Array(instruction, value) => (instruction, value.toInt)
    })
    .foldLeft((0, 0)) {
      case ((horizontalPosition, depth), ("forward", value)) => (horizontalPosition + value, depth)
      case ((horizontalPosition, depth), ("down", value)) => (horizontalPosition, depth + value)
      case ((horizontalPosition, depth), ("up", value)) => (horizontalPosition, depth - value)
    } match {
      case (horizontalPosition, depth) => horizontalPosition * depth
    }
}
