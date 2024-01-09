package rui.aoc.year2022.day4

case class Pair(p0: Int, p1: Int) {
  def contains(other: Pair): Boolean = p0 <= other.p0 && other.p1 <= p1
  def overlaps(other: Pair): Boolean =
    if (p0 < other.p0) p1 >= other.p0
    else other.p1 >= p0
}

object Pair {
  def fromStr(pairStr: String): Pair = pairStr.split("-").map(_.toInt) match {
    case Array(p0, p1) => Pair(p0, p1)
  }
}
