package rui.aoc.year2022.day21

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day21.Day21._
import rui.aoc.year2022.utils.FileIO

class Day21Part1 extends ProblemSolution {
  override def solve(): AnyVal = monkeyYellValue(FileIO
    .readResourceLines("day21.txt")
    .map(Monkey.apply)
    .map(monkey => monkey.name -> monkey)
    .toMap, ROOT_MONKEY_NAME)
}