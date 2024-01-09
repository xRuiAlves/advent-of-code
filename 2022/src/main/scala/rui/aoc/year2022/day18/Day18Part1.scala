package rui.aoc.year2022.day18

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day18.Day18.{Coord3D, adjacentCoords, parseCoords}
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class Day18Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val cubes = parseCoords(FileIO.readResourceLines("day18.txt"))
    cubes
      .toArray
      .map(cube => 6 - adjacentCoordsWithout(cubes, cube).size)
      .sum
  }

  def adjacentCoordsWithout(cubes: Set[Coord3D], cube: Coord3D): Set[Coord3D] = adjacentCoords(cube).intersect(cubes)
}
