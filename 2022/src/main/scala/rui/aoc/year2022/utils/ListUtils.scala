package rui.aoc.year2022.utils

object  ListUtils {
  def mergeIntervals(intervals: List[(Int, Int)]): List[(Int, Int)] = mergeIntervals(intervals.sorted, List())

  def mergeIntervals(intervals: List[(Int, Int)], mergedIntervals: List[(Int, Int)]): List[(Int, Int)] = intervals match {
    case head :: next :: tail if head._2 >= next._1 =>
      mergeIntervals((head._1, Math.max(head._2, next._2)) :: tail, mergedIntervals)
    case head :: next :: tail => mergeIntervals(next :: tail, mergedIntervals :+ head)
    case head :: Nil => mergedIntervals :+ head
    case _ => mergedIntervals
  }
}
