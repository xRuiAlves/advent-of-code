package rui.aoc.year2022.day15

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO
import rui.aoc.year2022.utils.ListUtils.mergeIntervals

class Day15Part1 extends ProblemSolution {
  private final val TARGET_Y = 2000000

  override def solve(): AnyVal = {
    val positions = FileIO
      .readResourceLines("day15.txt")
      .map(CoordinatePair.fromStr)
    val intervals = positions
      .map(coordinatePair => (
        coordinatePair.sensor._1 - (coordinatePair.manhattanDistance - math.abs(coordinatePair.sensor._2 - TARGET_Y)),
        coordinatePair.sensor._1 + (coordinatePair.manhattanDistance - math.abs(coordinatePair.sensor._2 - TARGET_Y))
      ))
      .toList

    val lineInterval = mergeIntervals(intervals).head
    lineInterval._2 - lineInterval._1
  }
}
