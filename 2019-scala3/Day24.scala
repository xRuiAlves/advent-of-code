//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day24 {
  type Map2D = Array[Array[Char]]

  private[this] final val Bug = '#'
  private[this] final val Empty = '.'

  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day24.txt")

    val t0 = System.nanoTime
    val (part1, part2) = getSolution(input)
    val t1 = System.nanoTime
    val duration = (t1 - t0) / 1e6d

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
    println(s"Duration: $duration ms")
  }

  def getSolution(input: Array[String]): (Int, Int) = {
    val map = input.map(_.toCharArray)
    val visited = mutable.Set[String]()

    @tailrec
    def getFirstRepeatedMap(map: Map2D): Map2D = {
      val hash = map.flatten.mkString
      if (visited.contains(hash)) map
      else {
        visited.addOne(hash)
        getFirstRepeatedMap(updateMap(map))
      }
    }

    val part1 = getBiodiversityRating(getFirstRepeatedMap(map))
    val part2 = 0

    (part1, part2)
  }

  def getBiodiversityRating(map: Map2D): Int = map
    .flatten
    .zipWithIndex
    .filter { case (cell, _) => cell == Bug }
    .map(_._2)
    .map(i => 1 << i)
    .sum

  def updateMap(map: Map2D): Map2D = {
    val newMap = Array.ofDim[Char](map.length, map.head.length)

    for (i <- map.indices; j <- map(i).indices) {
      val numAdjacentBugs = countAdjacentBugs(map, i, j)

      newMap(i)(j) = if (map(i)(j) == Bug) {
        if (numAdjacentBugs == 1) Bug else Empty
      } else {
        if (numAdjacentBugs == 1 || numAdjacentBugs == 2) Bug else Empty
      }
    }

    newMap
  }

  def countAdjacentBugs(map: Map2D, i: Int, j: Int): Int = Array(
    (i - 1, j),
    (i, j - 1),
    (i, j + 1),
    (i + 1, j)
  ).count { case (neighborI, neighborJ) =>
    isInBounds(map, neighborI, neighborJ) && map(neighborI)(neighborJ) == Bug
  }

  def isInBounds(map: Map2D, i: Int, j: Int): Boolean =
    i >= 0 && j >= 0 && i < map.length && j < map(i).length
}
