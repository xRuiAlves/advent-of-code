package rui.aoc.year2021.day25

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

import scala.annotation.tailrec

class Day25Part1 extends ProblemSolution {
  private[this] final val EmptyCell = '.'
  private[this] final val RightFacingCell = '>'
  private[this] final val DownFacingCell = 'v'

  override def solve(): AnyVal = step(FileIO.readResourceLines("day25.txt").map(_.toCharArray))

  @tailrec
  private final def step(map: Array[Array[Char]], stepNumber: Int = 1): Int = {
    val cells = (for (i <- map.indices; j <- map(i).indices) yield (i, j)).toSet

    def move(cellType: Char, deltaI: Int, deltaJ: Int): Boolean = {
      val toMove = cells.filter { case (i, j) => map(i)(j) == cellType &&
        map((i + deltaI) % map.length)((j + deltaJ) % map(i).length) == EmptyCell
      }
      toMove.foreach { case (i, j) =>
        map(i)(j) = EmptyCell
        map((i + deltaI) % map.length)((j + deltaJ) % map(i).length) = cellType
      }
      toMove.nonEmpty
    }

    val movedRight = move(RightFacingCell, 0, 1)
    val movedDown = move(DownFacingCell, 1, 0)

    if (movedRight || movedDown) step(map, stepNumber + 1)
    else stepNumber
  }
}
