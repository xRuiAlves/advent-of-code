package rui.aoc.year2022.day15

import rui.aoc.year2022.day15.CoordinatePair.Coordinate

case class CoordinatePair(sensor: Coordinate, beacon: Coordinate) {
  val manhattanDistance = math.abs(sensor._1 - beacon._1) + math.abs(sensor._2 - beacon._2)
}

object CoordinatePair {
  private final val COORDINATES_REGEX = "x=(-?\\d+), y=(-?\\d+)".r

  def fromStr(rawCoordinatePair: String): CoordinatePair = {
    val matches = COORDINATES_REGEX.findAllIn(rawCoordinatePair).matchData.toArray
    CoordinatePair(
      (matches(0).group(1).toInt, matches(0).group(2).toInt),
      (matches(1).group(1).toInt, matches(1).group(2).toInt),
    )
  }

  type Coordinate = (Int, Int)
}
