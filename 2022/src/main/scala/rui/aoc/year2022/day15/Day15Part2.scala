package rui.aoc.year2022.day15

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day15.CoordinatePair.Coordinate
import rui.aoc.year2022.utils.FileIO
import rui.aoc.year2022.utils.ListUtils.mergeIntervals

class Day15Part2 extends ProblemSolution {
  private final val MAX_COORD = 4000000

  override def solve(): AnyVal = {
    val positions: Array[CoordinatePair] = FileIO
      .readResourceLines("day15.txt")
      .map(CoordinatePair.fromStr)
    for (i <- 0 to MAX_COORD) {
      val intervals = positions
        .filter(coordinatePair => math.abs(coordinatePair.manhattanDistance) > math.abs(coordinatePair.sensor._2 - i))
        .map(coordinatePair => (
          coordinatePair.sensor._1 - (coordinatePair.manhattanDistance - math.abs(coordinatePair.sensor._2 - i)),
          coordinatePair.sensor._1 + (coordinatePair.manhattanDistance - math.abs(coordinatePair.sensor._2 - i))
        ))
        .toList

      mergeIntervals(intervals).sliding(2).filter(_.size > 1).find(isTargetIntervalPair) match {
        case Some(intervalPair) => return MAX_COORD.toLong * (intervalPair.head._2 + 1) + i
        case _ =>
      }
    }
  }

  private def isTargetIntervalPair(intervalPair: List[(Int, Int)]): Boolean = {
    def isTwoUnitsApart(intervalPair: List[(Int, Int)]): Boolean = intervalPair.head._2 + 2 == intervalPair(1)._1
    def isInBounds(intervalPair: List[(Int, Int)]): Boolean =
      intervalPair.head._2 + 1 >= 0 && intervalPair.head._2 + 1 <= MAX_COORD

    isTwoUnitsApart(intervalPair) && isInBounds(intervalPair)
  }

}
