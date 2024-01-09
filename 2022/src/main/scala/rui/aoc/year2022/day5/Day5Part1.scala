package rui.aoc.year2022.day5

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day5.Day5.{getMoveMatches, parseStacks}
import rui.aoc.year2022.utils.FileIO

class Day5Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val input = FileIO.readResourceLines("day5.txt")
    val stacks = parseStacks(input)

    getMoveMatches(input).foreach(move => {
      for (_ <- 0 until move.numCrates) {
        stacks(move.to - 1) push stacks(move.from - 1).pop()
      }
    })

    CrateWord(stacks.map(_.pop()).mkString)
  }
}
