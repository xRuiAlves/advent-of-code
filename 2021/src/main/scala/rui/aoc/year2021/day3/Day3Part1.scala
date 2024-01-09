package rui.aoc.year2021.day3

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day3Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val numbers = FileIO.readResourceLines("day3.txt")
    val occurrences = Array.fill(numbers.head.length){0}

    numbers.foreach(_.zipWithIndex.foreach {
      case (bit, i) => occurrences(i) += (if (bit == '0') -1 else 1)
    })

    occurrences.foldLeft((0, 0)) {
      case ((gamma, epsilon), curr) => (
        2 * gamma + (if (curr > 0) 1 else 0),
        2 * epsilon +  (if (curr > 0) 0 else 1)
      )
    } match {
      case (gamma, epsilon) => gamma * epsilon
    }
  }
}
