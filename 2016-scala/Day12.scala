//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day12 {
  type Registers = Map[String, Int]

  private[this] final val TargetRegister = "a"
  private[this] final val Registers = List("a", "b", "c", "d").map(_ -> 0).toMap

  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day12.txt")

    val part1 = applyInstructions(input, 0, Registers)(TargetRegister)
    val part2 = applyInstructions(input, 0, Registers.updated("c", 1))(TargetRegister)

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  @tailrec
  def applyInstructions(instructions: Array[String], ip: Int, registers: Registers): Registers = {
    if (ip >= instructions.length) registers
    else {
      val InstructionResult(newRegisters, ipDelta) = applyInstruction(instructions(ip), registers)
      applyInstructions(instructions, ip + ipDelta, newRegisters)
    }
  }

  case class InstructionResult(registers: Registers, ipDelta: Int)

  def applyInstruction(instruction: String, registers: Registers): InstructionResult = instruction match {
    case s"cpy $op1 $op2" => InstructionResult(registers.updated(op2, getValue(registers, op1)), 1)
    case s"inc $op"       => InstructionResult(registers.updated(op, registers(op) + 1), 1)
    case s"dec $op"       => InstructionResult(registers.updated(op, registers(op) - 1), 1)
    case s"jnz $op1 $op2" => InstructionResult(registers, if (getValue(registers, op1) == 0) 1 else op2.toInt)
  }

  def getValue(registers: Registers, op: String): Int =
    if (op.head.isLetter) registers(op)
    else op.toInt
}
