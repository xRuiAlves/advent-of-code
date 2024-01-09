package rui.aoc.year2022.day10

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day10.Day10.computeCycleValues
import rui.aoc.year2022.utils.FileIO

class Day10Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val cycleValues = computeCycleValues(FileIO.readResourceLines("day10.txt"))

    val ctrDisplayStr = cycleValues
      .zipWithIndex
      .foldLeft(Vector.empty[Char]) {
        case (crt, (value, index)) =>
          if ((value - 1 to value + 1).contains(index % 40)) crt.appended('#')
          else crt.appended('.')
      }
      .dropRight(1)
      .grouped(40)
      .map(_.mkString)
      .mkString("\n")
    CrtDisplay(ctrDisplayStr)
  }
}
