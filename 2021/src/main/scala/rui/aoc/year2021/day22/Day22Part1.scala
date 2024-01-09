package rui.aoc.year2021.day22

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.day22.Day22.{applyInstruction, cubesVolume, parseInstructions}
import rui.aoc.year2021.day22.Day22Part1.isInBounds
import rui.aoc.year2021.utils.FileIO

class Day22Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val instructions = parseInstructions(FileIO.readResourceLines("day22.txt")).filter {
      case Instruction(_, cube) => isInBounds(cube)
    }
    cubesVolume(applyInstruction(instructions, Set.empty))
  }
}

object Day22Part1 {
  private final val MIN_COORD = -50
  private final val MAX_COORD = 50

  def isInBounds(cube: Cube): Boolean =
    cube.x0 >= MIN_COORD && cube.x0 <= MAX_COORD &&
    cube.x1 >= MIN_COORD && cube.x1 <= MAX_COORD &&
    cube.y0 >= MIN_COORD && cube.y0 <= MAX_COORD &&
    cube.y1 >= MIN_COORD && cube.y1 <= MAX_COORD &&
    cube.z0 >= MIN_COORD && cube.z0 <= MAX_COORD &&
    cube.z1 >= MIN_COORD && cube.z1 <= MAX_COORD

}
