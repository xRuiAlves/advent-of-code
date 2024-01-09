//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.collection.mutable

object Day18 {
  type Keys = Int
  type Mat2D = Array[Array[Char]]
  type Coord2D = (Int, Int)

  private[this] final val WallCell = '#'
  private[this] final val StartCell = '@'
  private[this] final val EmptyCell = '.'

  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day18.txt")
    
    val t0 = System.nanoTime
    val (part1, part2) = getSolution(input)
    val t1 = System.nanoTime
    val duration = (t1 - t0) / 1e6d
    
    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
    println(s"Duration: $duration ms")
  }

  def getSolution(input: Array[String]): (Int, Int) = {
    val map = input.map(_.toCharArray)
    val start = parseStart(map)
    val targetKeys = map.flatten
      .filter(_.isLower)
      .map(getKeyId)
      .foldLeft(0)(getUpdatedKeys)

    val part1 = bfs(map, Array(start), targetKeys)
    val startsPart2 = updateMapForPart2(map, start)
    val part2 = bfs(map, startsPart2, targetKeys)

    (part1, part2)
  }

  case class VisitNode(coord: Coord2D, keys: Keys)

  case class BfsNode(coords: Array[Coord2D], keys: Keys, depth: Int)

  def bfs(map: Mat2D, starts: Array[Coord2D], targetKeys: Keys): Int = {
    val visited = starts.map(_ => mutable.Set[VisitNode]())
    val toVisit = mutable.Queue[BfsNode]()
    toVisit.enqueue(BfsNode(starts, 0, 0))

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()

      if (curr.keys == targetKeys) {
        return curr.depth
      }

      curr.coords.zipWithIndex.foreach { case (coord, i) =>
        if (!visited(i).contains(VisitNode(coord, curr.keys))) {
          visited(i).addOne(VisitNode(coord, curr.keys))

          getNeighbors(map, coord).foreach(neighbor => {
            val cell = map(neighbor._1)(neighbor._2)
            val keys =
              if (cell.isLower) getUpdatedKeys(curr.keys, getKeyId(cell))
              else curr.keys

            if (
              cell.isLower || cell == EmptyCell || (cell.isUpper && canOpenDoor(
                curr.keys,
                cell
              ))
            ) {
              toVisit.enqueue(
                BfsNode(curr.coords.updated(i, neighbor), keys, curr.depth + 1)
              )
            }
          })
        }
      }
    }

    throw new Error("Solution not found!")
  }

  def getNeighbors(map: Mat2D, coord: Coord2D): Array[Coord2D] = coord match {
    case (i, j) =>
      Array((i - 1, j), (i + 1, j), (i, j - 1), (i, j + 1))
        .filter(coord => isInBounds(map, coord))
        .filter { case (i, j) => map(i)(j) != WallCell }
  }

  def isInBounds(map: Mat2D, coord: Coord2D): Boolean = coord match
    case (i, j) => i >= 0 && j >= 0 && i < map.length && j < map(i).length

  def parseStart(map: Mat2D): Coord2D = {
    val startI = map.indexWhere(_.contains(StartCell))
    val startJ = map(startI).indexOf(StartCell)
    (startI, startJ)
  }

  def getUpdatedKeys(keys: Keys, newKey: Int): Keys = keys | newKey

  def getKeyId(key: Char): Int = 1 << (key - 'a')

  def canOpenDoor(keys: Keys, door: Char): Boolean =
    (keys & getKeyId(door.toLower)) != 0

  def updateMapForPart2(map: Mat2D, start: Coord2D): Array[Coord2D] =
    start match {
      case (i, j) =>
        map(i - 1)(j - 1) = EmptyCell
        map(i - 1)(j + 1) = EmptyCell
        map(i + 1)(j - 1) = EmptyCell
        map(i + 1)(j + 1) = EmptyCell

        map(i)(j) = WallCell
        map(i - 1)(j) = WallCell
        map(i + 1)(j) = WallCell
        map(i)(j - 1) = WallCell
        map(i)(j + 1) = WallCell

        Array(
          (i - 1, j - 1),
          (i - 1, j + 1),
          (i + 1, j - 1),
          (i + 1, j + 1)
        )
    }
}
