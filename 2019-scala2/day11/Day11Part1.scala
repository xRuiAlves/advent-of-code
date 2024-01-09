package day11

import utils.FileReader

import scala.collection.mutable

object Day11Part1 {
    final val PROGRAM_BUFFER_SIZE = 2000
    final val MAP_SIZE = 101

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day11/input.txt").toArray
        val pr = new ProgramRunner(createProgram(file_lines))
        val panel = Array.ofDim[Byte](MAP_SIZE, MAP_SIZE)

        val painted = new mutable.HashSet[(Int, Int)]()
        var pos = (MAP_SIZE / 2, MAP_SIZE / 2)
        var dir: (Byte, Byte) = (-1, 0)

        while (true) {
            val output = pr.run(panel(pos._2)(pos._1))

            if (output.isEmpty) {
                println(painted.size)
                return
            }

            val new_color = output(0)
            val turn_direction = output(1)

            panel(pos._2)(pos._1) = new_color.toByte
            painted += pos

            dir = getNewDir(dir, turn_direction.toByte)
            pos = (pos._1 + dir._1, pos._2 + dir._2)
        }

        throw new Exception("Solution not found")
    }

    def createProgram(raw_input: Array[String]): Array[BigInt] = {
        val original_program : Array[BigInt] = raw_input(0).split(",").map(BigInt(_))
        val program : Array[BigInt] = Array.fill(PROGRAM_BUFFER_SIZE)(0)
        Array.copy(original_program, 0, program, 0, original_program.length)
        program
    }

    def getNewDir(dir: (Byte, Byte), turn_direction: Byte): (Byte, Byte) = turn_direction match {
        case 0 => {
            if (dir == (0, -1)) (-1, 0)
            else if (dir == (-1, 0)) (0, 1)
            else if (dir == (0, 1)) (1, 0)
            else (0, -1)
        }
        case 1 => {
            if (dir == (0, -1)) (1, 0)
            else if (dir == (1, 0)) (0, 1)
            else if (dir == (0, 1)) (-1, 0)
            else (0, -1)
        }
    }
}
