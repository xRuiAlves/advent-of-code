package rui.aoc.year2022.day5

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Day5 {
  private final val MOVE_STACKS_REGEX = "^move (?<numCrates>\\d+) from (?<from>\\d+) to (?<to>\\d+)$".r
  private final val NUM_STACKS = 9

  def parseStacks(input: Array[String]): Array[mutable.Stack[Char]] = {
    val stacks: Array[mutable.Stack[Char]] = new Array[mutable.Stack[Char]](NUM_STACKS)
      .map(_ => new mutable.Stack[Char])

    input
      .takeWhile(!_.startsWith(" 1"))
      .foreach(line => fillStacks(stacks, line))

    stacks.map(_.reverse)
  }

  def fillStacks(stacks: Array[mutable.Stack[Char]], line: String): Unit = {
    val crates = ArrayBuffer[Option[Char]]()
    for (i <- 0 until line.length by 4) {
      val crate = line.substring(i, i + 3)
      crates.addOne(
        if (crate.startsWith("[")) Option.apply(crate(1))
        else Option.empty
      )
    }

    for (i <- crates.indices) crates(i) match {
      case Some(crate) => stacks(i) push crate
      case _ => None
    }
  }

  def getMoveMatches(input: Array[String]): Array[Move] = input
    .map(MOVE_STACKS_REGEX.findAllMatchIn(_))
    .dropWhile(_.isEmpty)
    .map(_.next())
    .map(matchResult => Move(
      matchResult.group("numCrates").toInt,
      matchResult.group("from").toInt,
      matchResult.group("to").toInt
    ))
}

case class Move(numCrates: Int, from: Int, to: Int)

case class CrateWord(value: String) extends AnyVal {
  override def toString: String = value
}
