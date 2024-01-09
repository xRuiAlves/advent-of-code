package rui.aoc.year2022.day20

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day20.Day20.{getNodeWithValue, getNth, groveCoordinatesSum, parseList, rotateList}
import rui.aoc.year2022.utils.FileIO

class Day20Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val list = parseList(FileIO.readResourceLines("day20.txt"))
    rotateList(list)
    groveCoordinatesSum(list)
  }
}