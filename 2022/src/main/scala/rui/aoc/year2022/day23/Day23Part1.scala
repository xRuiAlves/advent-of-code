package rui.aoc.year2022.day23

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day23.Day23._
import rui.aoc.year2022.utils.FileIO

class Day23Part1 extends ProblemSolution {
  private final val NUM_ROUNDS = 10

  override def solve(): AnyVal =
    regionEmptyGround(simulate(parseElfCoords(FileIO.readResourceLines("day23.txt"))))

  def simulate(elfs: Set[Coord], round: Int = 0): Set[Coord] = if (round == NUM_ROUNDS) elfs else {
    val proposedPositionsByEachElf = elfs
      .filter(elf => hasNeighbor(elf, elfs))
      .map(elf => elf -> findElfProposedPosition(elf, elfs, round))
      .filter {
        case (_, maybeProposedPosition) => maybeProposedPosition.isDefined
      }
      .map {
        case (elf, maybeProposedPosition) => elf -> maybeProposedPosition.get
      }
      .toMap
    val proposedPositionsCounts = proposedPositionsByEachElf.values.groupBy(identity).map {
      case (proposedPosition, proposedPositionGroup) => proposedPosition -> proposedPositionGroup.size
    }

    val movingElfs = proposedPositionsByEachElf.filter {
      case (_, proposedPosition) => proposedPositionsCounts(proposedPosition) == 1
    }

    simulate(elfs.diff(movingElfs.keySet).union(movingElfs.values.toSet), round + 1)
  }
}