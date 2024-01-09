package rui.aoc.year2022.day21

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day21.Day21._
import rui.aoc.year2022.utils.FileIO

class Day21Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val rawMonkeysOpsLines = FileIO.readResourceLines("day21.txt")
    val rootMonkeyLine = rawMonkeysOpsLines.find(_.startsWith(ROOT_MONKEY_NAME)).get
    val updatedRootMonkeyLine = rootMonkeyLine.replace("+", "-")
    val monkeysOpsLines = rawMonkeysOpsLines.filter(!_.startsWith(ROOT_MONKEY_NAME)).appended(updatedRootMonkeyLine)
    val monkeys = monkeysOpsLines
      .map(Monkey.apply)
      .map(monkey => monkey.name -> monkey)
      .toMap

    findMonkey0YellValue(monkeys)
  }

  def findMonkey0YellValue(monkeys: Map[String, Monkey], lowerBound: Long = 0, upperBound: Long = 10000000000000L): Long = {
    val guess = lowerBound + (upperBound - lowerBound) / 2
    val monkeyYell = monkeyYellWithHumanValue(monkeys, guess)

    if (monkeyYell == 0) decreaseHumanValueWhileYellEquals0(monkeys, guess)
    else if (monkeyYell > 0) findMonkey0YellValue(monkeys, guess + 1, upperBound)
    else findMonkey0YellValue(monkeys, lowerBound, guess - 1)

  }

  def monkeyYellWithHumanValue(monkeys: Map[String, Monkey], humanValue: Long): Long = monkeyYellValue(
    monkeys.removed(HUMAN_MONKEY_NAME) + (HUMAN_MONKEY_NAME -> Monkey.apply(s"$HUMAN_MONKEY_NAME: $humanValue")),
    ROOT_MONKEY_NAME
  )

  def decreaseHumanValueWhileYellEquals0(monkeys: Map[String, Monkey], humanYellValue: Long): Long =
    monkeyYellWithHumanValue(monkeys, humanYellValue - 1) match {
      case 0 => decreaseHumanValueWhileYellEquals0(monkeys, humanYellValue - 1)
      case _ => humanYellValue
    }
}