package rui.aoc.year2021

import scala.collection.mutable

object Main {
  final val FLAG_PREFIX = "--"
  final val DAY_FLAG = "--day"
  final val PART_FLAG = "--part"

  def main(args: Array[String]): Unit = {
    val flags = parseFlags(args)
    val day = flags.get(DAY_FLAG).map(_.toInt)
    val part = flags.get(PART_FLAG).map(_.toInt)
    validateInput(day, part)
    solve(day.get, part.get)
  }

  def solve(day: Int, part: Int): AnyVal = {
    val t0 = System.nanoTime
    val result = Class
      .forName(s"${this.getClass.getPackageName}.day${day}.Day${day}Part${part}")
      .getDeclaredConstructor()
      .newInstance()
      .asInstanceOf[ProblemSolution]
      .solve()
    val t1 = System.nanoTime
    val duration = (t1 - t0) / 1e6d
    println(s"Duration: $duration ms")
    println(s"Solution:\n$result")
  }

  def validateInput(day: Option[Int], part: Option[Int]) = {
    if (day.isEmpty) throw new Exception("The day must be specified")
    if (part.isEmpty) throw new Exception("The day part must be specified")
    if (!Seq(1, 2).contains(part.get)) throw new Exception("The day part must be either 1 or 2")
  }

  def parseFlags(args: Array[String]): mutable.Map[String, String] = {
    val flags = mutable.Map[String, String]()
    args.fold("")((last, curr) => last match {
      case "" => if (curr.startsWith(FLAG_PREFIX)) curr else ""
      case _ => {
        flags(last) = curr
        ""
      }
    })
    flags
  }
}
