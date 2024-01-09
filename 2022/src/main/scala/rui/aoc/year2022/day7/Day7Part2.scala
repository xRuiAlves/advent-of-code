package rui.aoc.year2022.day7

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day7.Day7.parseDirNodes
import rui.aoc.year2022.day7.Day7Part2.{TARGET_UNUSED_SPACE, TOTAL_DISK_SPACE}
import rui.aoc.year2022.utils.FileIO

class Day7Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val dirNodes = parseDirNodes(FileIO.readResourceLines("day7.txt"))
    val targetDecrease = dirNodes("/").getSize - (TOTAL_DISK_SPACE - TARGET_UNUSED_SPACE)
    dirNodes
      .values
      .map(_.getSize)
      .filter(_ > targetDecrease)
      .min
  }
}

object Day7Part2 {
  private final val TOTAL_DISK_SPACE = 70000000
  private final val TARGET_UNUSED_SPACE = 30000000
}
