package rui.aoc.year2022.day9

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day9Part2 extends ProblemSolution {

  def maybeUpdateTrailingPosition(tailPosition: Position, headPosition: Position): Position = {
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

    val tailPositions = mutable.Set[Position]()
    var positions = Array.fill(10) {
      (0, 0)
    }
    tailPositions.addOne((0, 0))

    moves.foreach(move => for (_ <- 0 until move.steps) {
      move.dir match {
        case 'R' => {
          positions(0) = (positions(0)._1 + 1, positions(0)._2)
          for (i <- 1 until positions.size) {
            positions(i) = maybeUpdateTrailingPosition(positions(i), positions(i - 1))
          }
        }
        case 'L' => {
          positions(0) = (positions(0)._1 - 1, positions(0)._2)
          for (i <- 1 until positions.size) {
            positions(i) = maybeUpdateTrailingPosition(positions(i), positions(i - 1))
          }
        }
        case 'U' => {
          positions(0) = (positions(0)._1, positions(0)._2 + 1)
          for (i <- 1 until positions.size) {
            positions(i) = maybeUpdateTrailingPosition(positions(i), positions(i - 1))
          }
        }
        case 'D' => {
          positions(0) = (positions(0)._1, positions(0)._2 - 1)
          for (i <- 1 until positions.size) {
            positions(i) = maybeUpdateTrailingPosition(positions(i), positions(i - 1))
          }
        }
      }
      tailPositions.addOne(positions(9))
    })

    tailPositions.size
  }

  type Position = (Int, Int)
  case class Move(dir: Char, steps: Int)
}
