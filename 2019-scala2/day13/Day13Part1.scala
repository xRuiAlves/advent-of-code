package day13

import utils.FileReader

object Day13Part1 {
    final val PROGRAM_BUFFER_SIZE = 3000

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day13/input.txt").toArray
        val program = createProgram(file_lines)
        val pr = new ProgramRunner(program)

        val stdout = pr.run
        val screen_tiles = new Array[(Int, Int, Int)](stdout.length / 3)

        for (i <- screen_tiles.indices) {
            screen_tiles(i) = (
                stdout(i * 3 + 0),
                stdout(i * 3 + 1),
                stdout(i * 3 + 2)
            )
        }

        val block_tiles = screen_tiles.filter(tile => tile._3 == 2)
        println(block_tiles.length)
    }



    def createProgram(raw_input: Array[String]): Array[BigInt] = {
        val original_program : Array[BigInt] = raw_input(0).split(",").map(BigInt(_))
        val program : Array[BigInt] = Array.fill(PROGRAM_BUFFER_SIZE)(0)
        Array.copy(original_program, 0, program, 0, original_program.length)
        program
    }
}
