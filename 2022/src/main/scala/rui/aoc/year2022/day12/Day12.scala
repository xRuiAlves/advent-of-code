package rui.aoc.year2022.day12

import scala.collection.mutable

object Day12 {
  def parseMap(rawInput: Array[String]): Map = rawInput.map(_.toCharArray)

  def findSymbol(map: Map, target: Char): Coordinate = {
    for (i <- map.indices; j <- map(i).indices) {
      if (map(i)(j) == target) return (j, i)
    }
    throw new Exception("Destination not found")
  }

  def findNeighbors(
    map: Map,
    position: Coordinate,
    isNeighborValid: (Map, Coordinate, Coordinate) => Boolean
  ): Seq[Coordinate] = Seq(
    (position._1 - 1, position._2),
    (position._1 + 1, position._2),
    (position._1, position._2 - 1),
    (position._1, position._2 + 1))
    .filter(_._1 >= 0)
    .filter(_._2 >= 0)
    .filter(_._2 < map.length)
    .filter(_._1 < map.head.length)
    .filter(neighbor => isNeighborValid(map, position, neighbor)
  )

  def findMinPath(
    start: Coordinate,
    map: Map,
    isDestination: (Map, Coordinate) => Boolean,
    isNeighborValid: (Map, Coordinate, Coordinate) => Boolean
  ): Int = {
    val queue = mutable.Queue[Coordinate]()
    val visited: mutable.Set[Coordinate] = mutable.Set()
    queue.enqueue(start)

    var distance = 0
    while (queue.nonEmpty) {
      for (_ <- queue.indices) {
        val curr = queue.dequeue()
        if (!visited.contains(curr)) {
          visited.addOne(curr)

          if (isDestination(map, curr)) {
            return distance
          }

          findNeighbors(map, curr, isNeighborValid).foreach(neighbor => queue.enqueue(neighbor))
        }
      }
      distance += 1
    }

    throw new Exception("Path not found")
  }

  type Coordinate = (Int, Int)
  type Map = Array[Array[Char]]

  final val START = 'S'
  final val DESTINATION = 'E'
  final val MIN_HEIGHT = 'a'
  final val MAX_HEIGHT = 'z'
}
