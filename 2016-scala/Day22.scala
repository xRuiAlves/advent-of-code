//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day22 {
  private[this] final val Part2TargetCoord = Coord(0, 0)

  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day22.txt")
    val nodes = parseNodes(input)

    val part1 = countViableNodePairs(nodes)
    val part2 = countFewestSteps(nodes)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class Coord(x: Int, y: Int) {
    def distanceTo(other: Coord): Int = math.abs(x - other.x) + math.abs(y - other.y)
  }

  object Coord {
    def fromStr(coordStr: String): Coord = coordStr match {
      case s"/dev/grid/node-x$x-y$y" => Coord(x.toInt, y.toInt)
    }
  }

  case class Node(coord: Coord, used: Int, size: Int) {
    lazy final val available: Int = size - used
    lazy final val isEmpty: Boolean = used == 0
  }

  object Node {
    def fromArray(arr: Array[String]): Node = arr match
      case Array(fs, used, size) =>
        Node(
          Coord.fromStr(fs),
          parseSpace(size),
          parseSpace(used)
        )

    def parseSpace(spaceStr: String): Int = spaceStr.dropRight(1).toInt
  }

  case class SearchState(
      distance: Int,
      targetCoord: Coord,
      emptyCoord: Coord,
      map: Map[Coord, Node]
  )

  case class VisitState(targetCoord: Coord, emptyCoord: Coord)

  def getNodesMap(nodes: Array[Node]): Map[Coord, Node] = nodes
    .map(node => node.coord -> node)
    .toMap

  def countFewestSteps(nodes: Array[Node]): Int = {
    val toVisit = mutable.Queue[SearchState]()
    val visited = mutable.Set[VisitState]()

    toVisit.enqueue(
      SearchState(
        distance = 0,
        targetCoord = nodes.map(_.coord).maxBy(_.x),
        emptyCoord = nodes.find(_.isEmpty).map(_.coord).get, // Any empty node is fine at this point
        map = getNodesMap(nodes)
      )
    )

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()

      if (curr.targetCoord == Part2TargetCoord)
        return curr.distance

      val visitState = VisitState(curr.targetCoord, curr.emptyCoord)
      if (!visited.contains(visitState)) {
        visited.addOne(visitState)

        getNeighbors(curr.map, curr.emptyCoord)
          .filter(neighbor => curr.map(curr.emptyCoord).size >= curr.map(neighbor).used)
          .foreach(neighbor => {
            val currEmpty = curr.map(curr.emptyCoord)
            val neighborNode = curr.map(neighbor)

            // Move from neighborNode to "currEmpty" node
            val newMap = curr.map
              .updated(neighbor, neighborNode.copy(used = 0))
              .updated(curr.emptyCoord, currEmpty.copy(used = neighborNode.used))

            toVisit.enqueue(
              SearchState(
                curr.distance + 1,
                if (neighbor == curr.targetCoord) curr.emptyCoord else curr.targetCoord,
                neighbor,
                newMap
              )
            )
          })
      }
    }

    throw new Error("Solution not found!")
  }

  def getNeighbors(map: Map[Coord, Node], coord: Coord): Set[Coord] = coord match {
    case Coord(x, y) =>
      Set(
        Coord(x - 1, y),
        Coord(x + 1, y),
        Coord(x, y - 1),
        Coord(x, y + 1)
      ).filter(map.contains)
  }

  def parseNodes(input: Array[String]): Array[Node] = input
    .drop(2)
    .map(_.split(" ").filter(_.nonEmpty).dropRight(2))
    .map(Node.fromArray)

  def countViableNodePairs(nodes: Array[Node]): Int = nodes
    .flatMap(nodeA => nodes.map(nodeB => (nodeA, nodeB)))
    .count { case (nodeA, nodeB) =>
      nodeA != nodeB && !nodeA.isEmpty && nodeB.available >= nodeA.used
    }
}
