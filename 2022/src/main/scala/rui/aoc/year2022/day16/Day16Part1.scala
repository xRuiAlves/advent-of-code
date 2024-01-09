package rui.aoc.year2022.day16

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day16.Day16.{findBestFlowProduction, parseGraph}
import rui.aoc.year2022.utils.FileIO

class Day16Part1 extends ProblemSolution {
  private final val TIME = 30

  override def solve(): AnyVal = findBestFlowProduction(
    parseGraph(FileIO.readResourceLines("day16.txt")),
    TIME
  )
}
