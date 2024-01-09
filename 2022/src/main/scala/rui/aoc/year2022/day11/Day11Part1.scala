package rui.aoc.year2022.day11

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day11.Day11.{parseMonkeys, processRounds}
import rui.aoc.year2022.utils.FileIO

class Day11Part1 extends ProblemSolution {
  override def solve(): AnyVal = processRounds(
    parseMonkeys(FileIO.readResourceLines("day11.txt")),
    20,
    modifyWorryLevel
  )

  def modifyWorryLevel(curr: Long): Long = curr / 3
}
