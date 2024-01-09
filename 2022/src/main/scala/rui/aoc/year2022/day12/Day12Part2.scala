package rui.aoc.year2022.day12

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day12.Day12.{Coordinate, DESTINATION, MAX_HEIGHT, MIN_HEIGHT, Map, START, findMinPath, findSymbol, parseMap}
import rui.aoc.year2022.utils.FileIO

class Day12Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val map = parseMap(FileIO.readResourceLines("day12.txt"))

    val sPosition = findSymbol(map, START)
    val start = findSymbol(map, DESTINATION)
    map(start._2)(start._1) = MAX_HEIGHT
    map(sPosition._2)(sPosition._1) = MIN_HEIGHT

    findMinPath(start, map, isDestination, isNeighborValid)
  }

  def isDestination(map: Map, coordinate: Coordinate): Boolean = {
    map(coordinate._2)(coordinate._1) == 'a'
  }

  def isNeighborValid(map: Map, curr: Coordinate, target: Coordinate): Boolean =
    map(curr._2)(curr._1) <= map(target._2)(target._1) + 1
}
