package rui.aoc.year2022.day3

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day3Part1 extends ProblemSolution {

  override def solve(): AnyVal = FileIO
    .readResourceLines("day3.txt")
    .map(line => {
      val lineBreakingIndex = line.length / 2
      val part1 = line.substring(0, lineBreakingIndex)
      val part2 = line.substring(lineBreakingIndex)
      (items(part1) intersect items(part2)).toSeq.head
    })
    .map(itemPriority)
    .sum

  def items(itemsStr: String): Set[Char] = itemsStr.toCharArray.toSet

  def itemPriority(item: Char): Int = item match {
    case _ if item.isLower => 1 + item - 'a'
    case _ if item.isUpper => 27 + item - 'A'
    case _ => throw new Exception("Unexpected item!")
  }
}
