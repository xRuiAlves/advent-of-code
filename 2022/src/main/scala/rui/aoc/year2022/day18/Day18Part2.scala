package rui.aoc.year2022.day18

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day18.Day18.{Coord3D, adjacentCoords}
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day18Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val cubes = FileIO
      .readResourceLines("day18.txt")
      .map(_.split(",").map(_.toInt) match {
        case Array(x, y, z) => (x, y, z)
      })
      .toSet

    val bounds = Bounds(
      cubes.map(_._1).min - 1,
      cubes.map(_._1).max + 1,
      cubes.map(_._2).min - 1,
      cubes.map(_._2).max + 1,
      cubes.map(_._3).min - 1,
      cubes.map(_._3).max + 1
    )

    val visited = mutable.Set[Coord3D]()
    val toVisit = mutable.Stack[Coord3D]()
    var numEdges = 0
    toVisit.push(bounds.min)

    while (toVisit.nonEmpty) {
      val coord = toVisit.pop()
      if (bounds.isIn(coord) && !visited.contains(coord)) {
        if (cubes.contains(coord)) numEdges += 1
        else {
          visited.addOne(coord)
          toVisit.pushAll(adjacentCoords(coord))
        }
      }
    }
    numEdges
  }

  case class Bounds(minX: Int, maxX: Int, minY: Int, maxY: Int, minZ: Int, maxZ: Int) {
    def isIn(coord: Coord3D): Boolean =
      coord._1 >= minX && coord._1 <= maxX &&
      coord._2 >= minY && coord._2 <= maxY &&
      coord._3 >= minZ && coord._3 <= maxZ

    def min: Coord3D = (minX, minY, minZ)
  }
}
