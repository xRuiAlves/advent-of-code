package day14

import utils.FileReader

import scala.collection.mutable

object Day14Part1 {
    final val ELEMENT_PATTERN_REGEX = "(\\d+) (\\w+)".r
    final val ORE = "ORE"
    final val FUEL = "FUEL"

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day14/input.txt").toArray

        val reactions_map = new mutable.HashMap[String, Array[(Int, String)]]()
        val reactions_quantities = new mutable.HashMap[String, Int]()
        val quantities = new mutable.HashMap[String, Int]().withDefaultValue(0)

        file_lines.foreach(line => {
            val results = ELEMENT_PATTERN_REGEX.findAllMatchIn(line).toList
            val elements = results.map(result => (result.group(1).toInt, result.group(2))).toArray

            reactions_map(elements.last._2) = elements.slice(0, elements.length - 1)
            reactions_quantities(elements.last._2) = elements.last._1
        })

        quantities(FUEL) = -1
        var progress = true

        while (progress) {
            progress = false

            quantities.foreach(entry => if (entry._1 != ORE && entry._2 < 0) {
                reactions_map(entry._1).foreach(elem => quantities(elem._2) -= elem._1)
                quantities(entry._1) += reactions_quantities(entry._1)
                progress = true
            })
        }

        println(math.abs(quantities(ORE)))
    }
}
