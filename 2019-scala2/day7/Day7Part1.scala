package day7

import utils.FileReader

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object Day7Part1 {
    final val MAX_THRUSTER_SIGNAL = 5
    final val START_INPUT_VAL = 0
    final val THRUSTER_PHASES = Array(0, 1, 2, 3, 4)

    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day7/input.txt").toArray
        val program = file_lines(0).split(",").map(_.toInt)

        var max_output = 0
        val thruster_configurations : ListBuffer[ArrayBuffer[Int]] = permutations(THRUSTER_PHASES)

        for (thruster_configuration <- thruster_configurations) {
            val oA = new ProgramRunner(program.clone(), List(thruster_configuration(0), START_INPUT_VAL)).run
            val oB = new ProgramRunner(program.clone(), List(thruster_configuration(1), oA._2)).run
            val oC = new ProgramRunner(program.clone(), List(thruster_configuration(2), oB._2)).run
            val oD = new ProgramRunner(program.clone(), List(thruster_configuration(3), oC._2)).run
            val oE = new ProgramRunner(program.clone(), List(thruster_configuration(4), oD._2)).run
            max_output = math.max(max_output, oE._2)
        }

        println(max_output)
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
