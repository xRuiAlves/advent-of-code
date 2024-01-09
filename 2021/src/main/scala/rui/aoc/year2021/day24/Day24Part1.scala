package rui.aoc.year2021.day24

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO
import rui.aoc.year2021.day24.Day24.{parseInstructions, evalInstructions}

class Day24Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val instructions = parseInstructions(FileIO.readResourceLines("day24.txt"))
    evalInstructions(instructions, (9 to 1 by -1).toArray).get.toLong
  }
}
