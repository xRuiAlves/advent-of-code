package rui.aoc.year2022.day7

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day7.Day7.parseDirNodes
import rui.aoc.year2022.day7.Day7Part1.FILE_SIZE_LOWER_BOUND
import rui.aoc.year2022.utils.FileIO

class Day7Part1 extends ProblemSolution {
  override def solve(): AnyVal = parseDirNodes(FileIO.readResourceLines("day7.txt"))
    .values
    .map(_.getSize)
    .filter(_ < FILE_SIZE_LOWER_BOUND)
    .sum
}

object Day7Part1 {
  private final val FILE_SIZE_LOWER_BOUND = 100000
}
