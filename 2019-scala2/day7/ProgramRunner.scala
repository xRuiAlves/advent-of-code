package day7

import scala.collection.mutable

class ProgramRunner(program: Array[Int], initial_stdin: List[Int] = List()) {
    val stdin: mutable.Queue[Int] = mutable.Queue(initial_stdin: _*)
    var stdout = 0
    var program_over = false
    var program_halted = false
    var pc = 0

    final class Instruction(raw_instruction: Int) {
        val opcode : Int = raw_instruction % 100
        val op1_mode : Int = (raw_instruction / 100) % 10
        val op2_mode : Int = (raw_instruction / 1000) % 10
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
        99 -> 1
    )

    def run: (Boolean, Int) = {
        if (program_over) throw  new Exception("Trying to run finished program")

        while (!program_over && !program_halted && pc < program.length) {
            execOperation
        }

        if (pc >= program.length) program_over = true

        program_halted = false
        (program_over, stdout)
    }

    def getVal(n: Int, mode: Int): Int = if (mode == 0) program(n) else n

    def sum(instruction: Instruction, op1: Int, op2: Int, op3: Int): Unit = {
        program(op3) = getVal(op1, instruction.op1_mode) + getVal(op2, instruction.op2_mode)
    }

    def mult(instruction: Instruction, op1: Int, op2: Int, op3: Int): Unit = {
        program(op3) = getVal(op1, instruction.op1_mode) * getVal(op2, instruction.op2_mode)
    }

    def input(op: Int): Unit = {
        program(op) = stdin.dequeue()
    }

    def output(op: Int): Unit = {
        program_halted = true
        stdout = program(op)
    }

    def jumpIfTrue(instruction: Instruction, op1: Int, op2: Int): Unit = {
        val v1 = getVal(op1, instruction.op1_mode)
        val v2 = getVal(op2, instruction.op2_mode)

        if (v1 != 0) {
            pc = v2 - instruction_jumps(instruction.opcode)
        }
    }

    def jumpIfFalse(instruction: Instruction, op1: Int, op2: Int): Unit = {
        val v1 = getVal(op1, instruction.op1_mode)
        val v2 = getVal(op2, instruction.op2_mode)

        if (v1 == 0) {
            pc = v2 - instruction_jumps(instruction.opcode)
        }
    }

    def lessThan(instruction: Instruction, op1: Int, op2: Int, op3: Int): Unit = {
        program(op3) = if (getVal(op1, instruction.op1_mode) < getVal(op2, instruction.op2_mode)) 1 else 0
    }

    def equals(instruction: Instruction, op1: Int, op2: Int, op3: Int): Unit = {
        program(op3) = if (getVal(op1, instruction.op1_mode) == getVal(op2, instruction.op2_mode)) 1 else 0
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
            case 5 => {
                jumpIfTrue(instruction, program(pc + 1), program(pc + 2))
            }
            case 6 => {
                jumpIfFalse(instruction, program(pc + 1), program(pc + 2))
            }
            case 7 => {
                lessThan(instruction, program(pc + 1), program(pc + 2), program(pc + 3))
            }
            case 8 => {
                equals(instruction, program(pc + 1), program(pc + 2), program(pc + 3))
            }
            case 99 => {
                program_over = true
            }
        }

        pc += instruction_jumps(instruction.opcode)
    }
}
