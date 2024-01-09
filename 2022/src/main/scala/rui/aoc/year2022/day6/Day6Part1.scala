package rui.aoc.year2022.day6

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day6.Day6.countCharsAtMarker
import rui.aoc.year2022.utils.FileIO

class Day6Part1 extends ProblemSolution {
  override def solve(): AnyVal = countCharsAtMarker(FileIO.readResourceLine("day6.txt"), 4)
}
