package rui.aoc.year2022.day23

object Day23 {
  final val PROPOSED_MOVE_GROUPS = IndexedSeq(
    ((0, -1), (1, -1), (-1, -1)),
    ((0, 1), (1, 1), (-1, 1)),
    ((-1, 0), (-1, -1), (-1, 1)),
    ((1, 0), (1, -1), (1, 1))
  )
  final val NEIGHBOR_COORDS = Set(
    (-1, -1), (0, -1), (1, -1),
    (-1, 0), (1, 0),
    (-1, 1), (0, 1), (1, 1)
  )

  def rotateLeft[A](arr: Seq[A], i: Int): Seq[A] = {
    arr.drop(i % arr.length) ++ arr.take(i % arr.length)
  }

  def parseElfCoords(elfMap: Array[String]): Set[Coord] = elfMap
    .map(_.toCharArray)
    .zipWithIndex
    .flatMap {
      case (line, y) => line.zipWithIndex.map {
        case (cell, x) => cell match {
          case '#' => Option((x, y))
          case '.' => Option.empty
        }
      }
    }
    .filter(_.isDefined)
    .map(_.get)
    .toSet

  def hasNeighbor(elf: Coord, elfs: Set[Coord]): Boolean = NEIGHBOR_COORDS.map {
    case (xDelta, yDelta) => (elf._1 + xDelta, elf._2 + yDelta)
  }.exists(neighborPosition => elfs.contains(neighborPosition))

  def findElfProposedPosition(elf: Coord, elfs: Set[Coord], round: Int): Option[Coord] = rotateLeft(PROPOSED_MOVE_GROUPS, round)
    .map {
      case (delta1, delta2, delta3) => Array(
        (elf._1 + delta1._1, elf._2 + delta1._2),
        (elf._1 + delta2._1, elf._2 + delta2._2),
        (elf._1 + delta3._1, elf._2 + delta3._2)
      )
    }
    .find(neigborCoords => !neigborCoords.exists(elfs.contains))
    .map(_.head)

  def regionEmptyGround(elfs: Set[Coord]): Int =
    (elfs.map(_._1).max - elfs.map(_._1).min + 1) * (elfs.map(_._2).max - elfs.map(_._2).min + 1) - elfs.size

  type Coord = (Int, Int)
}
