package rui.aoc.year2022.day23

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day23.Day23._
import rui.aoc.year2022.utils.FileIO

class Day23Part2 extends ProblemSolution {
  override def solve(): AnyVal = simulate(parseElfCoords(FileIO.readResourceLines("day23.txt")))

  def simulate(elfs: Set[Coord], round: Int = 0): Int = {
    val elfsWithNeighbors = elfs
      .filter(elf => hasNeighbor(elf, elfs))

    if (elfsWithNeighbors.isEmpty) {
      return round + 1
    }

    val proposedPositionsByEachElf = elfsWithNeighbors
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