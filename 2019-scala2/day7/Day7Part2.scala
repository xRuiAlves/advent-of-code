package day7

import utils.FileReader

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Day7Part2 {
    final val MAX_THRUSTER_SIGNAL = 5
    final val START_INPUT_VAL = 0
    final val THRUSTER_PHASES = Array(5, 6, 7, 8, 9)

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day7/input.txt").toArray
        val program = file_lines(0).split(",").map(_.toInt)

        var max_output = 0
        val thruster_configurations : ListBuffer[ArrayBuffer[Int]] = permutations(THRUSTER_PHASES)

        for (thruster_configuration <- thruster_configurations) {
            max_output = math.max(max_output, runThrusterConfiguration(program, thruster_configuration))
        }

        println(max_output)
    }

    def runThrusterConfiguration(program: Array[Int], thruster_configuration: ArrayBuffer[Int]): Int = {
        val pA = new ProgramRunner(program.clone())
        val pB = new ProgramRunner(program.clone())
        val pC = new ProgramRunner(program.clone())
        val pD = new ProgramRunner(program.clone())
        val pE = new ProgramRunner(program.clone())

        pA.stdin += thruster_configuration(0)
        pB.stdin += thruster_configuration(1)
        pC.stdin += thruster_configuration(2)
        pD.stdin += thruster_configuration(3)
        pE.stdin += thruster_configuration(4)

        pA.stdin += START_INPUT_VAL
        var output: Int = -1

        do {
            pB.stdin += pA.run._2
            pC.stdin += pB.run._2
            pD.stdin += pC.run._2
            pE.stdin += pD.run._2
            val res = pE.run

            if (res._1) {
                output = res._2
            } else {
                pA.stdin += res._2
            }
        } while(output == -1)

        output
    }

    def permutations(nums: Array[Int]): ListBuffer[ArrayBuffer[Int]] = {
        val permutations = new ListBuffer[ArrayBuffer[Int]]
        val used : Array[Boolean] = Array.fill(nums.length)(false)

        visit(nums, permutations, used, new ArrayBuffer[Int]())
        permutations
    }

    def visit(nums: Array[Int], permutations: ListBuffer[ArrayBuffer[Int]], used: Array[Boolean], curr: ArrayBuffer[Int]): Unit = {
        if (curr.size == nums.length) {
            permutations += curr.clone()
            return
        }

        for (i <- nums.indices) {
            if (!used(i)) {
                used(i) = true
                curr += nums(i)
                visit(nums, permutations, used, curr)
                curr.remove(curr.length - 1)
                used(i) = false
            }
        }
    }
}
