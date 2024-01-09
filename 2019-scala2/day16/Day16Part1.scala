package day16

import utils.FileReader

object Day16Part1 {
    final val MODIFIERS = Array(1, 0, -1, 0)
    final val NUM_PHASES = 100
    final val NUM_TARGET_DIGITS = 8

    def main(args: Array[String]): Unit = {
        val input: String = FileReader.readFile("src/day16/input.txt").toArray.last
        var signal: IndexedSeq[Int] = input.split("").map(_.toInt)

        for (i <- 0 until NUM_PHASES) {
            signal = applyPhase(signal)
        }

        println(signal.slice(0, NUM_TARGET_DIGITS).mkString(""))
    }

    def applyPhase(input_signal: IndexedSeq[Int]): IndexedSeq[Int] = input_signal.indices.map(i => applyPattern(input_signal, i))

    def applyPattern(signal: IndexedSeq[Int], idx: Int): Int = {
        val repeat_count = idx + 1
        val num_elems = signal.length - idx
        var sum = 0

        for (i <- 0 to idx) {
            for (j <- 0 until num_elems) {
                val position = idx + j*repeat_count + i
                if (position < signal.length) {
                    sum += signal(position) * MODIFIERS(j % MODIFIERS.length)
                }
            }
        }
        math.abs(sum) % 10
    }
}
