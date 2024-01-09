//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import Day17.MapValue._
import util.ResourceUtils.readResourceLines
import scala.collection.mutable

object Day17 {
  type Matrix2D = Array[Array[MapValue]]
  type Coord2D = (Int, Int)

  private[this] final val InitialStreamPosition: Coord2D = (500, 0)

  enum MapValue {
    case EMPTY, WALL, WATER, STREAM
  }

  def main(args: Array[String]): Unit = {
    val wallRegions = readResourceLines("day17.txt").map(Region.apply)
    val map = initMap(wallRegions)
    val streams = new mutable.Stack[Coord2D]()
    streams.push(InitialStreamPosition)

    while (streams.nonEmpty) {
      processStream(streams.pop())
    }

    def isEmptyUnder(x: Int, y: Int): Boolean =
      isInBounds(x, y) && isInBounds(x, y + 1) && map(y + 1)(x) == EMPTY

    def isInBounds(x: Int, y: Int): Boolean =
      y >= 0 && y < map.length && x >= 0 && x < map(y).length

    def processStream(streamCoord: Coord2D): Unit = streamCoord match
      case (x, y) =>
        var currY = y
        while (currY < map.length && map(currY)(x) != WALL) {
          map(currY)(x) = STREAM
          currY += 1
        }
        if (currY < map.length) {
          floodLine(x, currY - 1)
        }

    def floodLine(x: Int, y: Int): Unit = if (y >= 0) {
      map(y)(x) = WATER
      val hasStreamLeft = floodLeft(x - 1, y)
      val hasStreamRight = floodRight(x + 1, y)
      if (!hasStreamLeft && !hasStreamRight) floodLine(x, y - 1)
    }

    def floodLeft(x: Int, y: Int): Boolean = floodHorizontal(x, y, -1)
    def floodRight(x: Int, y: Int): Boolean = floodHorizontal(x, y, 1)
    def floodHorizontal(x: Int, y: Int, delta: Int): Boolean = if (
      isInBounds(x, y) && map(y)(x) != WALL
    ) {
      if (isEmptyUnder(x, y)) {
        addNewStream(x, y)
        true
      } else {
        map(y)(x) = WATER
        floodHorizontal(x + delta, y, delta)
      }
    } else false

    def addNewStream(x: Int, y: Int) = if (
      (isInBounds(x + 1, y + 1) && map(y + 1)(x + 1) == WALL) || (isInBounds(x - 1, y + 1) && map(
        y + 1
      )(x - 1) == WALL)
    )
      streams.push((x, y))

    def countWaterAtRest(x: Int, y: Int, acc: Int = 0): Int =
      if (!isInBounds(x, y)) 0
      else if (map(y)(x) == WALL) acc
      else if (map(y)(x) == WATER) countWaterAtRest(x + 1, y, acc + 1)
      else 0

    lazy val part1 =
      map.map(_.count(e => e == WATER || e == STREAM)).sum - wallRegions.map(_.y0).min
    lazy val part2 = map.indices
      .map(y =>
        map(y).indices
          .map(x =>
            if (map(y)(x) == WALL) countWaterAtRest(x + 1, y)
            else 0
          )
          .sum
      )
      .sum

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  def initMap(wallRegions: Array[Region]): Matrix2D = {
    val map: Matrix2D = Array
      .ofDim[MapValue](
        wallRegions.map(_.y1).max + 1,
        wallRegions.map(_.x1).max + 1
      )
      .map(row => row.map(_ => EMPTY))

    wallRegions.foreach(region =>
      for (
        y <- region.y0 to region.y1;
        x <- region.x0 to region.x1
      ) {
        map(y)(x) = WALL
      }
    )

    map
  }

  case class Region(x0: Int, x1: Int, y0: Int, y1: Int)
  object Region {

    def apply(x0: String, x1: String, y0: String, y1: String): Region =
      Region(x0.toInt, x1.toInt, y0.toInt, y1.toInt)

    def apply(regionStr: String): Region = regionStr match
      case s"x=$x, y=$y0..$y1" => Region(x, x, y0, y1)
      case s"y=$y, x=$x0..$x1" => Region(x0, x1, y, y)
  }
}
