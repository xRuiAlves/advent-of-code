package rui.aoc.year2022.day16

case class ValveNeighbor(name: String, var distance: Int = 1)

object ValveNeighbor {
  def apply(name: String) = new ValveNeighbor(name)
}