package rui.aoc.year2022.day20

case class ListNode(value: Long, var prev: ListNode = null, var next: ListNode = null) {
  override def toString: String = value.toString
}
