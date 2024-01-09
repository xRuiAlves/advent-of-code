package rui.aoc.year2022.day14

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day14.Day14._
import rui.aoc.year2022.utils.FileIO

class Day14Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val gridInstructions = parseGridInstructions(FileIO.readResourceLines("day14.txt"))
    val gridMaxY = gridInstructions.map(_.map(_._2).max).max + 2
    val grid: Grid = buildGrid(1000, gridMaxY + 1)
    drawLine(grid, (0, gridMaxY), (grid.head.length - 1, gridMaxY))
    drawLines(grid, gridInstructions)

    var droppedSandCount = 0
    while (maybeDropSand(grid, isAllSandDropped)) {
      droppedSandCount += 1
    }
    droppedSandCount + 1
  }

  def isAllSandDropped(position: Coordinate): Boolean =
    position._1 == Day14.SAND_SOURCE_POSITION._1 && position._2 == Day14.SAND_SOURCE_POSITION._2
}
