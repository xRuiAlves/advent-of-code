//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day23 {
  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day23.txt")
    val nanobots = input.map(Nanobot.fromStr)
    val maxRadiusNanobot = nanobots.maxBy(_.radius)

    val part1 = nanobots.count(maxRadiusNanobot.isInRange)
    val part2 = findShortestDistance(nanobots)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class Nanobot(x: Int, y: Int, z: Int, radius: Int) {
    def isInRange(other: Nanobot): Boolean =
      manhattanDistance(x, y, z, other.x, other.y, other.z) <= radius
  }

  object Nanobot {
    def fromStr(nanobotStr: String): Nanobot = nanobotStr match {
      case s"pos=<$x,$y,$z>, r=$radius" => Nanobot(x.toInt, y.toInt, z.toInt, radius.toInt)
    }
  }

  def manhattanDistance(x1: Int, y1: Int, z1: Int, x2: Int, y2: Int, z2: Int): Int =
    math.abs(x1 - x2) + math.abs(y1 - y2) + math.abs(z1 - z2)

  case class QueueNode(distance: Int, private val isLineSegmentStart: Boolean) extends Ordered[QueueNode] {
    final val lineSegmentDelta: Int = if (isLineSegmentStart) 1 else -1
    def compare(that: QueueNode) = that.distance.compareTo(this.distance)
  }

  def findShortestDistance(nanobots: Array[Nanobot]): Int = {
    val toVisit = mutable.PriorityQueue[QueueNode]()

    toVisit.addAll(nanobots.flatMap { case Nanobot(x, y, z, radius) =>
      val distanceToOrigin = manhattanDistance(0, 0, 0, x, y, z)
      // For each Nanobot, take into account bot the minimum and maximum distance from the origin
      Array(
        QueueNode(math.max(0, distanceToOrigin - radius), true),
        QueueNode(distanceToOrigin + radius + 1, false)
      )
    })

    def search(count: Int = 0, maxCount: Int = 0, result: Int = 0): Int = if (toVisit.isEmpty) result
    else {
      val curr = toVisit.dequeue()
      val newCount = count + curr.lineSegmentDelta
      search(newCount, math.max(newCount, maxCount), if (newCount > maxCount) curr.distance else result)
    }

    search()
  }
}
