package day5

import utils.FileReader

object Day5Part1 {
    var program : Array[Int] = null
    var pc = 0
    var program_over = false

    final val stdin : Int = 1
    final val instruction_jumps = Map(
        1 -> 4,
        2 -> 4,
        3 -> 2,
        4 -> 2,
        99 -> 1
    )

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day5/input.txt").toArray
        program = file_lines(0).split(",").map(_.toInt)

        while (!program_over && pc < program.length) {
            execOperation
        }
    }

    def getVal(n: Int, mode: Int): Int = if (mode == 0) program(n) else n

    def sum(instruction: Instruction, op1: Int, op2: Int, op3: Int): Unit = {
        program(op3) = getVal(op1, instruction.op1_mode) + getVal(op2, instruction.op2_mode)
    }

    def mult(instruction: Instruction, op1: Int, op2: Int, op3: Int): Unit = {
        program(op3) = getVal(op1, instruction.op1_mode) * getVal(op2, instruction.op2_mode)
    }

    def input(op: Int): Unit = {
        program(op) = stdin
    }

    def output(op: Int): Unit = {
        println(program(op))
    }

    def execOperation: Unit = {
        val instruction: Instruction = new Instruction(program(pc))

        instruction.opcode match {
            case 1 => {
                sum(instruction, program(pc + 1), program(pc + 2), program(pc + 3))
            }
            case 2 => {
                mult(instruction, program(pc + 1), program(pc + 2), program(pc + 3))
            }
            case 3 => {
                input(program(pc + 1))
            }
            case 4 => {
                output(program(pc + 1))
            }
            case 99 => {
                program_over = true
            }
        }

        pc += instruction_jumps(instruction.opcode)
    }
}
