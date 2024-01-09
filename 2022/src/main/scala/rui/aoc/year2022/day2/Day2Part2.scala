package rui.aoc.year2022.day2

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day2Part2 extends ProblemSolution {
  override def solve(): AnyVal = FileIO
    .readResourceLines("day2.txt")
    .map(Round.fromStr)
    .map(computeScore)
    .sum

  def computeScore(round: Round): Int = {
    val shapeScore = round.myMove match {
      case "A" => 1
      case "B" => 2
      case "C" => 3
    }
    val outcomeScore = round.outcome match {
      case "X" => 0
      case "Y" => 3
      case "Z" => 6
    }
    shapeScore + outcomeScore
  }

  case class Round(opponentMove: String, myMove: String, outcome: String)
  object Round {
    def fromStr(roundStr: String): Round = {
      roundStr.split(" ") match {
        case Array(opponentMove, outcome) => Round(opponentMove, MOVES_I_SHOULD_PLAY((opponentMove, outcome)), outcome)
      }
    }

    final private val MOVES_I_SHOULD_PLAY = Map(
      (("A", "X"), "C"),
      (("A", "Y"), "A"),
      (("A", "Z"), "B"),
      (("B", "X"), "A"),
      (("B", "Y"), "B"),
      (("B", "Z"), "C"),
      (("C", "X"), "B"),
      (("C", "Y"), "C"),
      (("C", "Z"), "A")
    )
  }
}
