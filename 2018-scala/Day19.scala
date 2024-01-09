//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using file util/NumUtils.scala
//> using resourceDir inputs

import util.NumUtils.divisors
import util.ResourceUtils.readResourceLines

import scala.collection.mutable
import scala.annotation.tailrec

object Day19 {
  private[this] case class Instruction(opcode: String, op1: Int, op2: Int, op3: Int)

  def main(args: Array[String]): Unit = {
    val input: Array[String] = readResourceLines("day19.txt")

    val ipRegister = input.head.split(" ").last.toInt
    val instructions = input
      .drop(1)
      .map(_.split(" ").toList)
      .map {
        case List(opcode, op1, op2, op3) => Instruction(opcode, op1.toInt, op2.toInt, op3.toInt)
        case _                           => throw new Error("Invalid input line!")
      }

    def runProgram(
        registers: Array[Int],
        ip: Int = 0,
        numIters: Option[Int] = Option.empty
    ): Array[Int] =
      if (ip >= instructions.length || numIters.exists(_ <= 0)) registers
      else {
        registers(ipRegister) = ip
        val instruction = instructions(ip)

        registers(instruction.op3) = instruction.opcode match {
          case "addi" => registers(instruction.op1) + instruction.op2
          case "addr" => registers(instruction.op1) + registers(instruction.op2)
          case "seti" => instruction.op1
          case "setr" => registers(instruction.op1)
          case "mulr" => registers(instruction.op1) * registers(instruction.op2)
          case "muli" => registers(instruction.op1) * instruction.op2
          case "eqrr" => if (registers(instruction.op1) == registers(instruction.op2)) 1 else 0
          case "gtrr" => if (registers(instruction.op1) > registers(instruction.op2)) 1 else 0
        }

        runProgram(registers, registers(ipRegister) + 1, numIters.map(_ - 1))
      }

    val part1 = runProgram(Array(0, 0, 0, 0, 0, 0)).head
    println(s"Part 1: $part1")

    // After A LOT of debugging and banging my head against this, I noticed there were some patterns in the registers array
    // (such as numbers repeating themselves in different columns). After trying to sum, divide, and multiply a bunch of
    // them to try and understand what on earth was going on, finally realized that the program was computing the sum of
    // the factors of a number! Which is actually super simple. After running for a while, the last register will stay the
    // same forever (I'm running it for 1000 iterations to make sure some time passes). Then, we can just compute the sum
    // of the factors for that number!
    val part2 = divisors(runProgram(Array(1, 0, 0, 0, 0, 0), numIters = Some(1000)).last).sum
    println(s"Part 2: $part2")
  }
}
