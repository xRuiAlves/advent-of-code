//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day25 {
  type Registers = Map[String, Int]

  private[this] final val BufferMaxSize = 20

  def main(args: Array[String]): Unit = {
    val instructions = readResourceLines("day25.txt")

    @tailrec
    def findInfiniteStream(startValue: Int = 0): Int = {
      if (isInfiniteStreamProgram(instructions.clone(), startValue)) startValue
      else findInfiniteStream(startValue + 1)
    }

    val solution = findInfiniteStream()
    println(s"Solution: $solution")
  }

  def isInfiniteStreamProgram(instructions: Array[String], startValue: Int): Boolean = {
    val buffer = mutable.ArrayBuffer[Int]()
    val registers = List("a", "b", "c", "d").map(_ -> 0).toMap.to(collection.mutable.Map)

    def getValue(op: String): Int = if (op.head.isLetter) registers(op) else op.toInt

    @tailrec
    def applyInstructions(ip: Int = 0): Unit = {
      if (ip < instructions.length && buffer.length < BufferMaxSize) applyInstructions(applyInstruction(ip))
    }

    def applyInstruction(ip: Int): Int = instructions(ip) match {
      case s"cpy $op1 $op2" =>
        registers(op2) = getValue(op1)
        ip + 1
      case s"inc $op" =>
        registers(op) += 1
        ip + 1
      case s"dec $op" =>
        registers(op) -= 1
        ip + 1
      case s"jnz $op1 $op2" =>
        ip + (if (getValue(op1) == 0) 1 else getValue(op2))
      case s"tgl $op" =>
        val toggleIp = ip + getValue(op)
        if (toggleIp < instructions.length) {
          instructions(toggleIp) = toggle(instructions(toggleIp))
        }
        ip + 1
      case s"out $op" =>
        buffer.addOne(getValue(op))
        ip + 1
    }

    registers("a") = startValue
    applyInstructions()
    isInfiniteStreamBuffer(buffer)
  }

  def isInfiniteStreamBuffer(buffer: mutable.ArrayBuffer[Int]): Boolean = buffer.zipWithIndex.forall {
    case (num, index) => num == index % 2
  }

  def toggle(instruction: String): String = {
    if (instruction.split(" ").length - 1 == 1) {
      if (instruction.startsWith("inc")) instruction.replace("inc", "dec")
      else s"inc${instruction.drop(3)}"
    } else {
      if (instruction.startsWith("jnz")) instruction.replace("jnz", "cpy")
      else s"jnz${instruction.drop(3)}"
    }
  }
}
