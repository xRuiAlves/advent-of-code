package day2

import utils.FileReader

object Day2Part1 {
    var program : Array[Int] = null
    var program_counter = 0
    var program_over = false

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day2/input.txt").toArray
        program = file_lines(0).split(",").map(_.toInt)

        putVal(1, 12)
        putVal(2, 2)

        while (!program_over && program_counter < program.length) {
            execOperation
        }

        println(getVal(0))
    }

    def getVal(idx: Int) : Int = program(idx)

    def putVal(idx: Int, value: Int): Unit = {
        program(idx) = value
    }

    def execOperation : Unit = {
        val operation = program(program_counter)

        operation match {
            case 1 => {
                putVal(getVal(program_counter + 3), getVal(getVal(program_counter + 1)) + getVal(getVal(program_counter + 2)))
                program_counter += 4
            }
            case 2 => {
                putVal(getVal(program_counter + 3), getVal(getVal(program_counter + 1)) * getVal(getVal(program_counter + 2)))
                program_counter += 4
            }
            case 99 => {
                program_over = true
            }
        }
    }
}
