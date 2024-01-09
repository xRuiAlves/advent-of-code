package rui.aoc.year2021.day22

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.day22.Day22.{applyInstruction, cubesVolume, parseInstructions}
import rui.aoc.year2021.utils.FileIO

class Day22Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val instructions = parseInstructions(FileIO.readResourceLines("day22.txt"))
    cubesVolume(applyInstruction(instructions, Set.empty))
  }
}
