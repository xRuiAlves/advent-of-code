package rui.aoc.year2022.day14

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day14.Day14.{Coordinate, Grid, buildGrid, drawLine, drawLines, maybeDropSand, parseGridInstructions}
import rui.aoc.year2022.utils.FileIO

class Day14Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val gridInstructions = parseGridInstructions(FileIO.readResourceLines("day14.txt"))
    val grid: Grid = buildGrid(1000, 1000)
    drawLines(grid, gridInstructions)

    var droppedSandCount = 0
    while (!maybeDropSand(grid)) {
      droppedSandCount += 1
    }
    droppedSandCount
  }
}
