package rui.aoc.year2022.day24

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day24.Day24._
import rui.aoc.year2022.utils.FileIO

class Day24Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val (matrix, windsCache) = readMatrixAndWinds(FileIO.readResourceLines("day24.txt"))
    val start = (1, 0)
    val target = (matrix.last.length - 2, matrix.length - 1)

    val t0 = findPath(matrix, start, target, 0, windsCache)
    val t1 = findPath(matrix, target, start, t0, windsCache)
    val t2 = findPath(matrix, start, target, t0 + t1, windsCache)

    t0 + t1 + t2
  }
}