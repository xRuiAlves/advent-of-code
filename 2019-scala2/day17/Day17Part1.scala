package day17

import utils.FileReader

object Day17Part1 {
    final val PROGRAM_BUFFER_SIZE = 5000

    final val SCAFFOLD = '#'
    final val EMPTY = '.'
    final val INVALID = '_'

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day17/input.txt").toArray
        val pr = new ProgramRunner(createProgram(file_lines))
        pr.run
        val map = pr.stdout.map(_.toChar).mkString("").split("\n").map(_.toCharArray)

        var sum = 0
        for (i <- map.indices; j <- map(i).indices) {
            if (isScaffoldIntersection(map, i, j)) {
                sum += i * j
            }
        }

        println(sum)
    }

    def isScaffoldIntersection(map: Array[Array[Char]], i: Int, j: Int): Boolean = {
        getValue(map, i, j) == SCAFFOLD &&
        getValue(map, i + 1, j) == SCAFFOLD &&
        getValue(map, i - 1, j) == SCAFFOLD &&
        getValue(map, i, j + 1) == SCAFFOLD &&
        getValue(map, i, j - 1) == SCAFFOLD
    }

    def getValue(map: Array[Array[Char]], i: Int, j: Int): Char = {
        if (isInBounds(map, i, j)) map(i)(j) else INVALID
    }

    def isInBounds(map: Array[Array[Char]], i: Int, j: Int): Boolean = {
        i >= 0 && j >= 0 && i < map.length && j < map(i).length
    }

    def createProgram(raw_input: Array[String]): Array[BigInt] = {
        val original_program : Array[BigInt] = raw_input(0).split(",").map(BigInt(_))
        val program : Array[BigInt] = Array.fill(PROGRAM_BUFFER_SIZE)(0)
        Array.copy(original_program, 0, program, 0, original_program.length)
        program
    }
}
