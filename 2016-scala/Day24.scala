//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines
import scala.collection.mutable

object Day24 {
  type Mat2D = Array[Array[Char]]
  type Coord2D = (Int, Int)

  private[this] final val EmptyCell = '.'
  private[this] final val WallCell = '#'
  private[this] final val StartCell = '0'

  def main(args: Array[String]): Unit = {
    val map = readResourceLines("day24.txt").map(_.toCharArray)
    val start = parseStart(map)
    val numPointsOfInterest = parseNumPointsOfInterest(map)

    val part1 = bfs(map, start, numPointsOfInterest)
    val part2 = bfs(map, start, numPointsOfInterest, terminateAtStart = true)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class VisitNode(coord: Coord2D, pointsOfInterest: Set[Char])

  case class BfsNode(visitNode: VisitNode, depth: Int)

  def bfs(map: Mat2D, start: Coord2D, numPointsOfInterest: Int, terminateAtStart: Boolean = false): Int = {
    val visited = mutable.Set[VisitNode]()
    val toVisit = mutable.Queue[BfsNode]()
    toVisit.enqueue(BfsNode(VisitNode(start, Set()), 0))

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()

      if (!visited.contains(curr.visitNode)) {
        visited.add(curr.visitNode)

        if (curr.visitNode.pointsOfInterest.size == numPointsOfInterest) {
          if (!terminateAtStart || (terminateAtStart && curr.visitNode.coord == start)) {
            return curr.depth
          }
        }

        toVisit.enqueueAll(getNeighbors(map, curr.visitNode.coord).map(neighbor => {
          val cell = map(neighbor._1)(neighbor._2)
          val pointsOfInterest = curr.visitNode.pointsOfInterest.concat(
            if (cell.isDigit && cell != '0') Set(cell)
            else Set()
          )
          BfsNode(VisitNode(neighbor, pointsOfInterest), curr.depth + 1)
        }))
      }
    }

    throw new Error("Solution not found!")
  }

  def getNeighbors(map: Mat2D, coord: Coord2D): Array[Coord2D] = coord match {
    case (i, j) =>
      Array((i - 1, j), (i + 1, j), (i, j - 1), (i, j + 1))
        .filter(coord => isInBounds(map, coord))
        .filter { case (i, j) => map(i)(j) != WallCell }
  }

  def isInBounds(map: Mat2D, coord: Coord2D): Boolean = coord match
    case (i, j) => i >= 0 && j >= 0 && i < map.length && j < map(i).length

  def parseStart(map: Mat2D): Coord2D = {
    val startI = map.indexWhere(_.contains('0'))
    val startJ = map(startI).indexOf('0')
    (startI, startJ)
  }

  def parseNumPointsOfInterest(map: Mat2D): Int = map.flatten.count(_.isDigit) - 1
}
