package rui.aoc.year2022.day24

import scala.collection.mutable

object Day24 {

  def buildWindsCache(
    winds: Winds,
    targetCacheSize: Int,
    cache: WindsCache = Map.empty,
  ): WindsCache = if (cache.size == targetCacheSize) cache else {
    buildWindsCache(
      winds.map(_.update),
      targetCacheSize,
      cache + (cache.size -> winds.map(wind => (wind.x, wind.y))),
    )
  }

  def readMatrixAndWinds(rawInput: Array[String]): (Matrix, WindsCache) = {
    val rawMatrix = rawInput.map(_.toCharArray)
    val windCicleSize = lcm(rawMatrix.length - 2, rawMatrix.head.length - 2)
    val clearMatrix = rawMatrix.map(_.map {
      case '#' => '#'
      case _ => '.'
    })

    (clearMatrix, buildWindsCache(parseWinds(rawMatrix), windCicleSize))
  }

  def parseWinds(rawMatrix: Matrix): Winds = rawMatrix
    .zipWithIndex
    .flatMap {
      case (line, y) => line.zipWithIndex.map {
        case (elem, x) => elem match {
          case '>' => Option(RightWind(x, y, rawMatrix.head.length - 2))
          case '<' => Option(LeftWind(x, y, rawMatrix.head.length - 2))
          case 'v' => Option(DownWind(x, y, rawMatrix.length - 2))
          case '^' => Option(UpWind(x, y, rawMatrix.length - 2))
          case _ => Option.empty
        }
      }
    }
    .filter(_.isDefined)
    .map(_.get)
    .toSet

  def isCoordInBounds(matrix: Matrix, coord: Coord): Boolean =
    coord._1 >= 0 && coord._2 >= 0 && coord._2 < matrix.length && coord._1 < matrix(coord._2).length

  def neighbors(matrix: Matrix, coord: Coord): Set[Coord] = Set(
    (coord._1 - 1, coord._2),
    (coord._1 + 1, coord._2),
    (coord._1, coord._2 - 1),
    (coord._1, coord._2 + 1)
  ).filter(coord => isCoordInBounds(matrix, coord))

  def emptyNeighbors(matrix: Matrix, coord: Coord): Set[Coord] = neighbors(matrix, coord).filter {
    case (x, y) => matrix(y)(x) == '.'
  }

  def gcd(a: Int, b: Int): Int =
    if (b == 0) a
    else gcd(b, a % b)

  def lcm(a: Int, b: Int): Int =
    (math.max(a, b) / gcd(a, b)) * math.min(a, b)

  def findPath(matrix: Matrix, startPos: Coord, targetPos: Coord, startingTime: Int, windsCache: WindsCache): Int = {
    val toVisit = mutable.Queue[Coord]()
    val visited = mutable.Set[VisitedState]()
    toVisit.enqueue(startPos)
    var time = startingTime

    while (toVisit.nonEmpty) {
      val levelSize = toVisit.length
      time += 1
      val winds = windsCache(time % windsCache.size)

      for (_ <- 0 until levelSize) {
        val curr = toVisit.dequeue()
        val currState = (curr._1, curr._2, time % windsCache.size)

        if (!visited.contains(currState)) {
          visited.addOne(currState)

          if (curr == targetPos) {
            return time - startingTime - 1
          }

          (emptyNeighbors(matrix, curr) + curr)
            .diff(winds)
            .foreach(toVisit.enqueue)
        }
      }
    }

    throw new Exception("Path not found!")
  }

  type Winds = Set[Wind]
  type WindsCache = Map[Int, Set[Coord]]
  type Matrix = Array[Array[Char]]
  type Coord = (Int, Int)
  type VisitedState = (Int, Int, Int)
}
