package day2

import utils.FileReader

object Day2Part2 {
    val TARGET_OUTPUT = 19690720

    var program : Array[Int] = null
    var program_counter = 0
    var program_over = false

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day2/input.txt").toArray
        val original_program = file_lines(0).split(",").map(_.toInt)

        for (noun <- 0 to 99; verb <- 0 to 99) {
            program = original_program.clone()
            program_counter = 0
            program_over = false

            putVal(1, noun)
            putVal(2, verb)

            while (!program_over && program_counter < program.length) {
                execOperation

                if (getVal(0) == TARGET_OUTPUT) {
                    println(100 * noun + verb)
                    return
                }
            }
        }

        throw new Exception("Answer not found")
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
