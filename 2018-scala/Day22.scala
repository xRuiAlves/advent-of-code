//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day22 {
  private[this] final val ErosionMultiplierX = 16807
  private[this] final val ErosionMultiplierY = 48271
  private[this] final val ErosionLevelModulo = 20183
  private[this] final val TypeModulo = 3

  def main(args: Array[String]): Unit = {
    val input = Input.fromArray(readResourceLines("day22.txt"))
    println(input)
    val geologicIndexes = mutable.Map[Coord, Int]()
    val start = Coord(0, 0)

    def getGeologicIndex(coord: Coord): Int = geologicIndexes.get(coord) match {
      case Some(geologicIndex) => geologicIndex
      case None =>
        geologicIndexes(coord) = (
          if (coord == start || coord == input.target) 0
          else if (coord.y == 0) coord.x * ErosionMultiplierX
          else if (coord.x == 0) coord.y * ErosionMultiplierY
          else getErosionLevel(Coord(coord.x, coord.y - 1)) * getErosionLevel(Coord(coord.x - 1, coord.y))
        ) % ErosionLevelModulo
        geologicIndexes(coord)
    }

    def getErosionLevel(coord: Coord): Int = (getGeologicIndex(coord) + input.depth) % ErosionLevelModulo

    def getRegionType(coord: Coord): Int = getErosionLevel(coord) % TypeModulo

    val map = Array.ofDim[Int](input.target.y + 1, input.target.x + 1)
    for (y <- map.indices; x <- map(y).indices) {
      map(y)(x) = getRegionType(Coord(x, y))
    }

    val part1 = map.map(_.sum).sum
    val part2 = 0

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class Coord(x: Int, y: Int)

  case class Input(target: Coord, depth: Int)

  object Input {
    def fromArray(arr: Array[String]): Input = arr match {
      case Array(s"depth: $depth", s"target: $x,$y") => Input(
        Coord(x.toInt, y.toInt),
        depth.toInt
      )
    }
  }
}
