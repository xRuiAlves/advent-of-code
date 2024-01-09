package rui.aoc.year2022.day11

import scala.collection.mutable.ArrayBuffer

object Day11 {
  def applyOp(item: Long, operand: Char, operationDelta: String): Long = (operand, operationDelta) match {
    case ('+', "old") => item + item
    case ('*', "old") => item * item
    case ('+', delta) => item + delta.toInt
    case ('*', delta) => item * delta.toInt
  }

  def parseMonkeys(rawMonkeysInput: Array[String]): Array[Monkey] = {
    rawMonkeysInput
      .grouped(7)
      .toArray
      .map(parseMonkey)
  }

  def parseMonkey(monkeyInput: Array[String]): Monkey = {
    val items = monkeyInput(1)
      .substring(18)
      .split(", ")
      .map(_.toLong)
      .to(ArrayBuffer)
    val operand = monkeyInput(2)(23)
    val operationDelta = monkeyInput(2).substring(25)
    val divTestDelta = monkeyInput(3).substring(21).toInt
    val trueTargetMonkey = monkeyInput(4).substring(29).toInt
    val falseTargetMonkey = monkeyInput(5).substring(30).toInt

    Monkey(items, operand, operationDelta, divTestDelta, trueTargetMonkey, falseTargetMonkey, 0)
  }

  def processRound(monkeys: Array[Monkey], modifyWorryLevel: Long => Long): Unit = {
    monkeys.foreach(monkey => {
      monkey.items.foreach(item => {
        val modifiedWorryLevel = modifyWorryLevel(applyOp(item, monkey.operand, monkey.operationDelta))
        if (modifiedWorryLevel % monkey.divTestDelta == 0) {
          monkeys(monkey.trueTargetMonkey).items.addOne(modifiedWorryLevel)
        } else {
          monkeys(monkey.falseTargetMonkey).items.addOne(modifiedWorryLevel)
        }
        monkey.numItemsInspected += 1
      })
      monkey.items.clear()
    })
  }

  def processRounds(monkeys: Array[Monkey], numRounds: Int, modifyWorryLevel: Long => Long): Long = {
    (0 until numRounds).foreach(_ => processRound(monkeys, modifyWorryLevel))
    monkeys
      .map(_.numItemsInspected)
      .sorted
      .map(_.toLong)
      .takeRight(2)
      .product
  }
}
