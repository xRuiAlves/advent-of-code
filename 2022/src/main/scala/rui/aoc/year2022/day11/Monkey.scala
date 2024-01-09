package rui.aoc.year2022.day11

import scala.collection.mutable.ArrayBuffer

case class Monkey(
  items: ArrayBuffer[Long],
  operand: Char,
  operationDelta: String,
  divTestDelta: Int,
  trueTargetMonkey: Int,
  falseTargetMonkey: Int,
  var numItemsInspected: Int
)
