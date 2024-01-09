package day13

import utils.FileReader

object Day13Part2 {
    final val PROGRAM_BUFFER_SIZE = 3000

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day13/input.txt").toArray
        val program = createProgram(file_lines)
        program(0) = 2
        val pr = new ProgramRunner(program)

        pr.run
        println(pr.score)
    }



    def createProgram(raw_input: Array[String]): Array[BigInt] = {
        val original_program : Array[BigInt] = raw_input(0).split(",").map(BigInt(_))
        val program : Array[BigInt] = Array.fill(PROGRAM_BUFFER_SIZE)(0)
        Array.copy(original_program, 0, program, 0, original_program.length)
        program
    }
}
