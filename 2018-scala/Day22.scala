//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day22 {
  private[this] final val ErosionMultiplierX = 16807
  private[this] final val ErosionMultiplierY = 48271
  private[this] final val ErosionLevelModulo = 20183
  private[this] final val TypeModulo = 3

  private[this] final val ToolSwitchCost = 7
  private[this] final val MoveCost = 1

  def main(args: Array[String]): Unit = {
    val input = Input.fromArray(readResourceLines("day22.txt"))

    val mapPart1 = buildMap(input, input.target.x + 1, input.target.y + 1)
    val mapPart2 = buildMap(input, input.target.x * 10, input.target.y * 10)

    val part1 = mapPart1.map(_.map(_.erosion).sum).sum
    val part2 = getShortestPath(mapPart2, input.target)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  type Map = Array[Array[RegionType]]

  enum RegionType(val erosion: Int) {
    case Rocky extends RegionType(0)
    case Wet extends RegionType(1)
    case Narrow extends RegionType(2)
  }

  enum Tool {
    case ClimbingGear, Torch, Neither
  }

  case class Coord(x: Int, y: Int)

  case class Input(target: Coord, depth: Int)

  object Input {
    def fromArray(arr: Array[String]): Input = arr match {
      case Array(s"depth: $depth", s"target: $x,$y") => Input(
        Coord(x.toInt, y.toInt),
        depth.toInt
      )
    }
  }

  def buildMap(input: Input, width: Int, height: Int): Map = {
    val geologicIndexes = mutable.Map[Coord, Int]()
    val start = Coord(0, 0)

    def getGeologicIndex(coord: Coord): Int = geologicIndexes.get(coord) match {
      case Some(geologicIndex) => geologicIndex
      case None =>
        geologicIndexes(coord) = (
          if (coord == start || coord == input.target) 0
          else if (coord.y == 0) coord.x * ErosionMultiplierX
          else if (coord.x == 0) coord.y * ErosionMultiplierY
          else getErosionLevel(Coord(coord.x, coord.y - 1)) * getErosionLevel(Coord(coord.x - 1, coord.y))
          ) % ErosionLevelModulo
        geologicIndexes(coord)
    }

    def getErosionLevel(coord: Coord): Int = (getGeologicIndex(coord) + input.depth) % ErosionLevelModulo

    def getType(coord: Coord): RegionType = RegionType.fromOrdinal(getErosionLevel(coord) % TypeModulo)

    val map = Array.ofDim[RegionType](height, width)
    for (y <- map.indices; x <- map(y).indices) {
      map(y)(x) = getType(Coord(x, y))
    }
    map
  }

  case class VisitState(coord: Coord, tool: Tool)

  case class SearchState(visitState: VisitState, distance: Int) extends Ordered[SearchState] {
    def compare(that: SearchState) = that.distance.compareTo(this.distance)
  }

  def getShortestPath(map: Map, target: Coord): Int = {
    val toVisit = mutable.PriorityQueue[SearchState]()
    val visited = mutable.Set[VisitState]()
    var solution: Option[Int] = None
    toVisit.enqueue(SearchState(VisitState(Coord(0, 0), Tool.Torch), 0))

    while (toVisit.nonEmpty) {
      if (solution.isDefined) {
        return solution.get
      }

      val curr = toVisit.dequeue()

      if (curr.visitState.coord == target && curr.visitState.tool == Tool.Torch) {
        solution = Some(math.min(curr.distance, solution.getOrElse(Int.MaxValue)))
      } else if (!visited.contains(curr.visitState)) {
        visited.addOne(curr.visitState)

        // Tool Switch
        toVisit.enqueue(SearchState(
          VisitState(curr.visitState.coord, getPossibleToolSwitch(map, curr.visitState)),
          curr.distance + ToolSwitchCost
        ))

        // Moving
        getNeighborCoords(map, curr.visitState).foreach(neighborCoord => {
          toVisit.enqueue(SearchState(VisitState(neighborCoord, curr.visitState.tool), curr.distance + MoveCost))
        })
      }
    }

    solution.get
  }

  def getPossibleToolSwitch(map: Map, visitState: VisitState): Tool = getPossibleToolSwitch(
    map(visitState.coord.y)(visitState.coord.x), visitState.tool
  )
  def getPossibleToolSwitch(regionType: RegionType, currTool: Tool): Tool = Tool
    .values
    .filter(_ != currTool)
    .filter(otherTool => isValidTool(regionType, otherTool))
    .head

  def getNeighborCoords(map: Map, visitState: VisitState): Array[Coord] = visitState.coord match {
    case Coord(x, y) => Array(
      Coord(x + 1, y),
      Coord(x - 1, y),
      Coord(x, y + 1),
      Coord(x, y - 1)
    )
      .filter(neighborCoord => isInBounds(map, neighborCoord))
      .filter(neighborCoord => isValidTool(map(neighborCoord.y)(neighborCoord.x), visitState.tool))
  }

  def isValidTool(regionType: RegionType, tool: Tool): Boolean = regionType match {
    case RegionType.Rocky => tool != Tool.Neither
    case RegionType.Wet => tool != Tool.Torch
    case RegionType.Narrow => tool != Tool.ClimbingGear
  }

  def isInBounds(map: Map, coord: Coord): Boolean =
    coord.x >= 0 && coord.y >= 0 && coord.y < map.length && coord.x < map(coord.y).length
}
