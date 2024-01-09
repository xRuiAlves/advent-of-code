package rui.aoc.year2022.day16

import scala.collection.mutable

case class Valve(name: String, flowRate: Int, var neighbors: mutable.Set[ValveNeighbor], var id: Int)

object Valve {
  private final val COORDINATES_REGEX =
    "Valve (?<name>\\w+) has flow rate=(?<flowRate>\\d+); tunnels? leads? to valves? (?<neighbors>.*)".r

  def fromStr(rawValveStr: String, id: Int): Valve = {
    val matches = COORDINATES_REGEX.findFirstMatchIn(rawValveStr).get
    Valve(
      matches.group("name"),
      matches.group("flowRate").toInt,
      matches.group("neighbors").split(", ").map(ValveNeighbor.apply).to(mutable.Set),
      id
    )
  }
}
