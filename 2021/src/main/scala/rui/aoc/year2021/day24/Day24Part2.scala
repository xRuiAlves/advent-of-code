package rui.aoc.year2021.day24

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO
import rui.aoc.year2021.day24.Day24.{parseInstructions, evalInstructions}

class Day24Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val instructions = Day24.parseInstructions(FileIO.readResourceLines("day24.txt"))
    evalInstructions(instructions, (1 to 9).toArray).get.toLong
  }
}
