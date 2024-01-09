package day9

import utils.FileReader

object Day9Part1 {
    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day9/input.txt").toArray

        val original_program : Array[BigInt] = file_lines(0).split(",").map(BigInt(_))
        val program : Array[BigInt] = Array.fill(3000)(0)
        Array.copy(original_program, 0, program, 0, original_program.length)

        val program_runner = new ProgramRunner(program)
        program_runner.stdin += 1
        program_runner.run
    }
}
