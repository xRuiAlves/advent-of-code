package rui.aoc.year2022.day3

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day3Part2 extends ProblemSolution {
  case class GroupAnalysis(priority: Int, groupSacks: List[Set[Char]])

  override def solve(): AnyVal = {
    FileIO
      .readResourceLines("day3.txt")
      .foldLeft(GroupAnalysis(0, List.empty[Set[Char]]))((groupAnalysis, ruckSackStr) => {
        val newGroup = groupAnalysis.groupSacks :+ items(ruckSackStr)
        if (newGroup.length == 3) {
          val badge = (newGroup(0) intersect newGroup(1) intersect newGroup(2)).toSeq.head
          GroupAnalysis(groupAnalysis.priority + itemPriority(badge), List.empty)
        }
        else GroupAnalysis(groupAnalysis.priority, newGroup)
      })
      .priority
  }

  def items(itemsStr: String): Set[Char] = itemsStr.toCharArray.toSet

  def itemPriority(item: Char): Int = item match {
    case _ if item.isLower => 1 + item - 'a'
    case _ if item.isUpper => 27 + item - 'A'
    case _ => throw new Exception("Unexpected item!")
  }
}
