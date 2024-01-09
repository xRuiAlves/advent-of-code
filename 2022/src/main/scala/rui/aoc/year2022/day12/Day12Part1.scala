package rui.aoc.year2022.day12

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day12.Day12.{Coordinate, DESTINATION, MAX_HEIGHT, MIN_HEIGHT, Map, START, findMinPath, findSymbol, parseMap}
import rui.aoc.year2022.utils.FileIO

class Day12Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val map = parseMap(FileIO.readResourceLines("day12.txt"))

    val start = findSymbol(map, START)
    val destination = findSymbol(map, DESTINATION)
    def isDestination(_map: Map, coordinate: Coordinate): Boolean = coordinate == destination

    map(start._2)(start._1) = MIN_HEIGHT
    map(destination._2)(destination._1) = MAX_HEIGHT

    findMinPath(start, map, isDestination, isNeighborValid)
  }

  def isNeighborValid(map: Map, curr: Coordinate, target: Coordinate): Boolean =
    map(curr._2)(curr._1) + 1 >= map(target._2)(target._1)
}
