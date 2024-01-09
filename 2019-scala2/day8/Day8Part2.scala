package day8

import utils.FileReader

object Day8Part2 {
    final val IMAGE_WIDTH = 25
    final val IMAGE_HEIGHT = 6
    final val IMAGE_SIZE = IMAGE_WIDTH * IMAGE_HEIGHT
    final val BLACK_PIXEL = 0
    final val WHITE_PIXEL = 1
    final val TRANSPARENT_PIXEL = 2

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day8/input.txt").toArray
        val buffer = file_lines(0)
        val num_layers = buffer.length / IMAGE_SIZE
        val layers = new Array[Array[Int]](num_layers)

        for (i <- 0 until num_layers) {
            layers(i) = buffer.substring(IMAGE_SIZE * i, IMAGE_SIZE * (i + 1)).split("").map(_.toInt)
        }

        val raw_image = new Array[Int](IMAGE_SIZE)

        for (i <- raw_image.indices) {
            var curr_layer = 0
            do {
                raw_image(i) = layers(curr_layer)(i)
                curr_layer += 1
            } while (raw_image(i) == TRANSPARENT_PIXEL)
        }

        val image: Array[Char] = raw_image.map(pixel => if (pixel == BLACK_PIXEL) ' ' else 'X')

        for (i <- 0 until IMAGE_HEIGHT) {
            println(image.slice(IMAGE_WIDTH * i, IMAGE_WIDTH * (i + 1)).mkString(""))
        }
    }

    def getPixelCount(layer: Array[Int], pixel: Int): Int = layer.count(_ == pixel)
}
