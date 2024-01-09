//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day11 {
  def main(args: Array[String]): Unit = {
    val part1Start = State(
      List(
        Floor(Set("SG", "SM", "PG", "PM")),
        Floor(Set("TG", "RG", "RM", "CG", "CM")),
        Floor(Set("TM")),
        Floor(Set())
      )
    )
    val part1 = findMinPath(part1Start)

    val part2Start = State(
      List(
        Floor(Set("SG", "SM", "PG", "PM", "EG", "EM", "DG", "DM")),
        Floor(Set("TG", "RG", "RM", "CG", "CM")),
        Floor(Set("TM")),
        Floor(Set())
      )
    )
    val part2 = findMinPath(part2Start)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class Floor(pieces: Set[String]) {
    private lazy val microchips = pieces.filter(_.last == 'M').map(_.head)
    private lazy val generators = pieces.filter(_.last == 'G').map(_.head)
    lazy val isValid: Boolean = microchips.forall(microchip => {
      generators.contains(microchip) || generators.isEmpty
    })
  }

  case class State(floors: List[Floor], elevator: Int = 0) {
    lazy val isValid: Boolean = floors.forall(_.isValid)
    lazy val isFinalState: Boolean = floors.dropRight(1).forall(_.pieces.isEmpty)

    def getValidMoves(): Seq[Move] = {
      val pieces = floors(elevator).pieces
      val singleMoves = pieces.map(Set(_))
      val doubleMoves = pieces.subsets(2).toSet
      val moves = singleMoves.concat(doubleMoves).toList
      val thisFloorAfterMoves = moves.map(move => Floor(pieces.removedAll(move)))
      val neighborFloors = List(elevator - 1, elevator + 1).filter(floors.indices.contains)

      neighborFloors
        .flatMap(otherFloor => {
          moves.zip(thisFloorAfterMoves).map { case (move, thisFloorAfterMove) =>
            val otherFloorAfterMove = Floor(floors(otherFloor).pieces.concat(move))
            Move(
              State(floors.updated(elevator, thisFloorAfterMove).updated(otherFloor, otherFloorAfterMove), otherFloor),
              math.abs(elevator - otherFloor)
            )
          }
        })
        .filter(_.state.isValid)
    }
  }

  case class Move(state: State, distance: Int)
  case class SearchNode(state: State, distance: Int)

  def findMinPath(start: State): Int = {
    val visited = mutable.Set[State]()
    val toVisit = mutable.Queue[SearchNode]()
    toVisit.enqueue(SearchNode(start, 0))

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()

      if (curr.state.isFinalState) {
        return curr.distance
      }

      if (!visited.contains(curr.state)) {
        visited.addOne(curr.state)

        toVisit.enqueueAll(
          curr.state
            .getValidMoves()
            .map(move => {
              SearchNode(move.state, curr.distance + move.distance)
            })
        )
      }
    }

    throw new Error("Solution not found!")
  }
}
