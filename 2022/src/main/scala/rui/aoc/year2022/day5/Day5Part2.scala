package rui.aoc.year2022.day5

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day5.Day5.{getMoveMatches, parseStacks}
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable.ArrayBuffer

class Day5Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val input = FileIO.readResourceLines("day5.txt")
    val stacks = parseStacks(input)

    getMoveMatches(input).foreach(move => {
      val cratesToMove = ArrayBuffer[Char]()
      for (_ <- 0 until move.numCrates) {
        cratesToMove addOne stacks(move.from - 1).pop()
      }
      cratesToMove.reverse.foreach(crate => stacks(move.to - 1).push(crate))
    })

    CrateWord(stacks.map(_.pop()).mkString)
  }
}