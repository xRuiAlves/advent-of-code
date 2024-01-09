package rui.aoc.year2022.day24

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day24.Day24._
import rui.aoc.year2022.utils.FileIO

class Day24Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val (matrix, windsCache) = readMatrixAndWinds(FileIO.readResourceLines("day24.txt"))
    val startPos = (1, 0)
    val targetPos = (matrix.last.length - 2, matrix.length - 1)
    findPath(matrix, startPos, targetPos, 0, windsCache)
  }
}