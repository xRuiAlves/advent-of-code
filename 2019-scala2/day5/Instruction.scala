package day5

class Instruction(raw_instruction: Int) {
    val opcode : Int = raw_instruction % 100
    val op1_mode : Int = (raw_instruction / 100) % 10
    val op2_mode : Int = (raw_instruction / 1000) % 10
}
