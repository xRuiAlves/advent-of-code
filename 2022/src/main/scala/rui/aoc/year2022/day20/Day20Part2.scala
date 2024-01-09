package rui.aoc.year2022.day20

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day20.Day20.{getNodeWithValue, getNth, groveCoordinatesSum, parseList, rotateList}
import rui.aoc.year2022.utils.FileIO

class Day20Part2 extends ProblemSolution {
  private final val DECRYPTION_KEY = 811589153

  override def solve(): AnyVal = {
    val list = parseList(
      FileIO.readResourceLines("day20.txt"),
      DECRYPTION_KEY
    )

    for (_ <- 0 until 10) rotateList(list)
    groveCoordinatesSum(list)
  }
}