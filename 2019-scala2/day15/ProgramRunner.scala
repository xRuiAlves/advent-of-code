package day15

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

class ProgramRunner(program: Array[BigInt]) {
    val stdin: mutable.Queue[BigInt] = mutable.Queue()
    var stdout = new ArrayBuffer[BigInt]()
    var program_over = false
    var pc: Int = 0
    var relative_base: BigInt = 0

    final class Instruction(raw_instruction: Int) {
        val opcode : Int = raw_instruction % 100
        val op_modes : Array[Int] = Array(
            (raw_instruction / 100) % 10,
            (raw_instruction / 1000) % 10,
            (raw_instruction / 10000) % 10
        )

        val arguments = new ArrayBuffer[Int]()
        for (i <- 1 until instruction_jumps(opcode)) {
            arguments += (op_modes(i - 1) match {
                case 0 => program(pc + i).toInt
                case 1 => pc + i
                case 2 => (program(pc + i) + relative_base).toInt
            })
        }
    }

    final val instruction_jumps = Map(
        1 -> 4,
        2 -> 4,
        3 -> 2,
        4 -> 2,
        5 -> 3,
        6 -> 3,
        7 -> 4,
        8 -> 4,
        9 -> 2,
        99 -> 1
    )

    def run(input: Int): BigInt = {
        stdin += input
        stdout.clear()
        while (!program_over && pc < program.length && stdout.isEmpty) {
            execOperation
        }
        stdout.last
    }

    def sum(arguments: ArrayBuffer[Int]): Unit = {
        program(arguments(2)) = program(arguments(0)) + program(arguments(1))
    }

    def mult(arguments: ArrayBuffer[Int]): Unit = {
        program(arguments(2)) = program(arguments(0)) * program(arguments(1))
    }

    def input(arguments: ArrayBuffer[Int]): Unit = {
        program(arguments(0)) = stdin.dequeue
    }

    def output(arguments: ArrayBuffer[Int]): Unit = {
        stdout += program(arguments(0))
    }

    def jumpIfTrue(instruction: Instruction): Unit = {
        if (program(instruction.arguments(0)) != 0) {
            pc = program(instruction.arguments(1)).toInt - instruction_jumps(instruction.opcode)
        }
    }

    def jumpIfFalse(instruction: Instruction): Unit = {
        if (program(instruction.arguments(0)) == 0) {
            pc = program(instruction.arguments(1)).toInt - instruction_jumps(instruction.opcode)
        }
    }

    def ifLessThan(arguments: ArrayBuffer[Int]): Unit = {
        program(arguments(2)) =
            if (program(arguments(0)) < program(arguments(1))) 1
            else 0
    }

    def ifEquals(arguments:ArrayBuffer[Int]): Unit = {
        program(arguments(2)) =
            if (program(arguments(0)) == program(arguments(1))) 1
            else 0
    }

    def adjustRelativeBase(arguments: ArrayBuffer[Int]): Unit = {
        relative_base += program(arguments(0))
    }

    def execOperation: Unit = {
        val instruction: Instruction = new Instruction(program(pc).toInt)

        instruction.opcode match {
            case 1 => sum(instruction.arguments)
            case 2 => mult(instruction.arguments)
            case 3 => input(instruction.arguments)
            case 4 => output(instruction.arguments)
            case 5 => jumpIfTrue(instruction)
            case 6 => jumpIfFalse(instruction)
            case 7 => ifLessThan(instruction.arguments)
            case 8 => ifEquals(instruction.arguments)
            case 9 => adjustRelativeBase(instruction.arguments)
            case 99 => program_over = true
        }

        pc += instruction_jumps(instruction.opcode)
    }
}
