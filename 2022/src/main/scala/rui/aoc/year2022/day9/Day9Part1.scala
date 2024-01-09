package rui.aoc.year2022.day9

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day9Part1 extends ProblemSolution {

  def maybeUpdateTailPosition(tailPosition: Position, headPosition: Position): Position = {
    if (tailPosition._1 < headPosition._1 - 1) (headPosition._1 - 1, headPosition._2)
    else if (tailPosition._1 > headPosition._1 + 1) (headPosition._1 + 1, headPosition._2)
    else if (tailPosition._2 < headPosition._2 - 1) (headPosition._1, headPosition._2 - 1)
    else if (tailPosition._2 > headPosition._2 + 1) (headPosition._1, headPosition._2 + 1)

    else tailPosition
  }

  override def solve(): AnyVal = {
    val moves = FileIO
      .readResourceLines("day9.txt")
      .map(_.split(" ") match {
        case Array(dir, step) => Move(dir.head, step.toInt)
      })

    var headPosition: Position = (0, 0)
    var tailPosition: Position = (0, 0)
    val tailPositions = mutable.Set[Position]()
    tailPositions.addOne(tailPosition)

    moves.foreach(move => for (_ <- 0 until move.steps) {
      move.dir match {
        case 'R' => {
          headPosition = (headPosition._1 + 1, headPosition._2)
          tailPosition = maybeUpdateTailPosition(tailPosition, headPosition)
        }
        case 'L' => {
          headPosition = (headPosition._1 - 1, headPosition._2)
          tailPosition = maybeUpdateTailPosition(tailPosition, headPosition)
        }
        case 'U' => {
          headPosition = (headPosition._1, headPosition._2 + 1)
          tailPosition = maybeUpdateTailPosition(tailPosition, headPosition)
        }
        case 'D' => {
          headPosition = (headPosition._1, headPosition._2 - 1)
          tailPosition = maybeUpdateTailPosition(tailPosition, headPosition)
        }
      }
      tailPositions.addOne(tailPosition)
    })
    tailPositions.size
  }

  type Position = (Int, Int)
  case class Move(dir: Char, steps: Int)
}
