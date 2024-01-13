//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.{readResourceLine, readResourceLines}

import scala.annotation.tailrec
import scala.collection.mutable

object Day2 {
  private[this] final val Sum = 1
  private[this] final val Mult = 2
  private[this] final val Halt = 99

  def main(args: Array[String]): Unit = {
    val input = readResourceLine("day2.txt")

    val t0 = System.nanoTime
    val (part1, part2) = getSolution(input)
    val t1 = System.nanoTime
    val duration = (t1 - t0) / 1e6d

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
    println(s"Duration: $duration ms")
  }

  def getSolution(input: String): (Int, Int) = {
    val part1 = runIntcode(input, Map(
      1 -> 12,
      2 -> 2
    ))
    val part2 = 0

    (part1, part2)
  }

  def runIntcode(program: String, memOverrides: Map[Int, Int] = Map()): Int = {
    val instructions = program.split(",").map(_.toInt)

    def sum(op1: Int, op2: Int, op3: Int) = {
      instructions(op3) = instructions(op1) + instructions(op2)
    }

    def mult(op1: Int, op2: Int, op3: Int) = {
      instructions(op3) = instructions(op1) * instructions(op2)
    }

    @tailrec
    def applyInstruction(ip: Int): Int =
      if (instructions(ip) == Halt) instructions(0)
      else if (instructions(ip) == Sum) {
        sum(instructions(ip + 1), instructions(ip + 2), instructions(ip + 3))
        applyInstruction(ip + 4)
      }
      else if (instructions(ip) == Mult) {
        mult(instructions(ip + 1), instructions(ip + 2), instructions(ip + 3))
        applyInstruction(ip + 4)
      }
      else throw new Error(s"Invalid instruction: ${instructions(ip)}")

    memOverrides.foreach {
      case (pointer, value) => instructions(pointer) = value
    }
    applyInstruction(0)
  }
}
