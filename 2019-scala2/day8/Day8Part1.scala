package day8

import utils.FileReader

object Day8Part1 {
    final val IMAGE_WIDTH = 25
    final val IMAGE_HEIGHT = 6
    final val IMAGE_SIZE = IMAGE_WIDTH * IMAGE_HEIGHT

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day8/input.txt").toArray
        val buffer = file_lines(0)
        val num_layers = buffer.length / IMAGE_SIZE
        val layers = new Array[Array[Int]](num_layers)

        for (i <- 0 until num_layers) {
            layers(i) = buffer.substring(IMAGE_SIZE * i, IMAGE_SIZE * (i + 1)).split("").map(_.toInt)
        }

        var fewest0_layer = 0
        var fewest0_count = Int.MaxValue

        for (i <- layers.indices) {
            val count = getPixelCount(layers(i), 0)

            if (count < fewest0_count) {
                fewest0_count = count
                fewest0_layer = i
            }
        }

        val res = getPixelCount(layers(fewest0_layer), 1) * getPixelCount(layers(fewest0_layer), 2)
        println(res)
    }

    def getPixelCount(layer: Array[Int], pixel: Int): Int = layer.count(_ == pixel)
}
