//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day25 {
  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day25.txt")
    val coords = input.map(Coord4D.fromStr)

    val visited = mutable.Set[Coord4D]()
    val result = coords.count(curr => {
      val isNewGalaxy = !visited.contains(curr)
      bfs(coords, curr, visited)
      isNewGalaxy
    })

    println(s"Result: $result")
  }

  case class Coord4D(x: Int, y: Int, z: Int, w: Int) {
    def isClose(other: Coord4D): Boolean = manhattanDistance(this, other) <= 3
  }

  object Coord4D {
    def fromStr(coordStr: String): Coord4D = coordStr match
      case s"$x,$y,$z,$w" => Coord4D(x.toInt, y.toInt, z.toInt, w.toInt)
  }

  def manhattanDistance(a: Coord4D, b: Coord4D): Int = (a, b) match
    case (Coord4D(x1, y1, z1, w1), Coord4D(x2, y2, z2, w2)) =>
      math.abs(x1 - x2) + math.abs(y1 - y2) + math.abs(z1 - z2) + math.abs(w1 - w2)

  def bfs(coords: Array[Coord4D], curr: Coord4D, visited: mutable.Set[Coord4D]): Unit = if (!visited.contains(curr)) {
    visited.add(curr)
    coords.filter(curr.isClose).foreach(closeCoord => bfs(coords, closeCoord, visited))
  }
}
