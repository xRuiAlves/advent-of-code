//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using file util/NumUtils.scala
//> using resourceDir inputs

import scala.collection.mutable
import scala.io.Source
import util.ResourceUtils.readResourceLine

object Day20 {
  type Coord2D = (Int, Int)

  private[this] final val FarRoomDistance = 1000
  private[this] final val DirectionSteps = Map(
    'N' -> (-1, 0),
    'E' -> (0, 1),
    'S' -> (1, 0),
    'W' -> (0, -1)
  )

  private[this] final val RegexGroupStart = '('
  private[this] final val RegexGroupEnd = ')'
  private[this] final val RegexGroupOr = '|'

  def main(args: Array[String]): Unit = {
    val regex = readResourceLine("day20.txt").toCharArray
      .drop(1)
      .dropRight(1)
    val graph = buildGraph(regex)

    val toVisit = mutable.Queue[Coord2D]()
    val roomDistances = mutable.Map[Coord2D, Int]()

    toVisit.enqueue((0, 0))
    roomDistances((0, 0)) = 0
    var furthestDistance = 0
    var numFarRooms = 0

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()
      furthestDistance = math.max(furthestDistance, roomDistances(curr))

      if (roomDistances(curr) >= FarRoomDistance) {
        numFarRooms += 1
      }

      graph(curr).foreach(neighbor => {
        if (
          !roomDistances.contains(neighbor) || roomDistances(neighbor) > roomDistances(curr) + 1
        ) {
          roomDistances(neighbor) = roomDistances(curr) + 1
          toVisit.enqueue(neighbor)
        }
      })
    }

    println(s"Part 1: $furthestDistance")
    println(s"Part 2: $numFarRooms")
  }

  def buildGraph(regex: Array[Char]): mutable.Map[Coord2D, mutable.Set[Coord2D]] = {
    val graph = mutable.Map[Coord2D, mutable.Set[Coord2D]]()

    def addGraphLink(node1: Coord2D, node2: Coord2D): Unit = {
      if (!graph.contains(node1)) {
        graph(node1) = mutable.Set[Coord2D]()
      }
      graph(node1).addOne(node2)
    }

    var positions = mutable.Set[Coord2D]()
    var starts = mutable.Set[Coord2D]()
    var ends = mutable.Set[Coord2D]()
    positions.addOne((0, 0))

    // Stack that keeps track of the current possible positions (in a (starts, ends) format)
    val regexGroupsStack = mutable.Stack[(mutable.Set[Coord2D], mutable.Set[Coord2D])]()

    regex.foreach {
      case RegexGroupStart =>
        regexGroupsStack.push((starts, ends))
        starts = positions
        ends.clear()
      case RegexGroupEnd =>
        positions.addAll(ends)
        starts = regexGroupsStack.top._1
        ends = regexGroupsStack.pop()._2
      case RegexGroupOr =>
        ends.addAll(positions)
        positions = starts
      case direction =>
        val (diffX, diffY) = DirectionSteps(direction)
        positions = positions.map { case curr @ (x, y) =>
          val next = (x + diffX, y + diffY)
          addGraphLink(curr, next)
          addGraphLink(next, curr)
          next
        }
    }

    graph
  }
}
