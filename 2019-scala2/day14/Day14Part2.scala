package day14

import utils.FileReader

import scala.collection.mutable

object Day14Part2 {
    final val ELEMENT_PATTERN_REGEX = "(\\d+) (\\w+)".r
    final val ORE = "ORE"
    final val FUEL = "FUEL"
    final val CARGO_HOLD = 1000000000000L

    final val reactions_map = new mutable.HashMap[String, Array[(Long, String)]]()
    final val reactions_quantities = new mutable.HashMap[String, Long]()

    def getNecessaryOre(target_fuel: Long): Long = {
        val quantities = new mutable.HashMap[String, Long]().withDefaultValue(0)

        quantities(FUEL) = -target_fuel
        var progress = true

        while (progress) {
            progress = false

            quantities.foreach(entry => if (entry._1 != ORE && entry._2 < 0) {
                val multiplier = math.ceil(math.abs(entry._2).toFloat / reactions_quantities(entry._1)).toLong
                reactions_map(entry._1).foreach(elem => quantities(elem._2) -= (elem._1 * multiplier))
                quantities(entry._1) += (reactions_quantities(entry._1) * multiplier)
                progress = true
            })
        }

        math.abs(quantities(ORE))
    }

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day14/input.txt").toArray

        file_lines.foreach(line => {
            val results = ELEMENT_PATTERN_REGEX.findAllMatchIn(line).toList
            val elements = results.map(result => (result.group(1).toLong, result.group(2))).toArray

            reactions_map(elements.last._2) = elements.slice(0, elements.length - 1)
            reactions_quantities(elements.last._2) = elements.last._1
        })

        val unitary_fuel = getNecessaryOre(1)
        val lb = CARGO_HOLD / unitary_fuel
        val ub = getNecessaryOre(lb)
        val offset: Double = CARGO_HOLD / ub.toDouble
        val estimate = math.ceil(offset * lb).toLong

        val max_fuel = ((estimate - 1) to (estimate + 2))
            .map(e => (e, getNecessaryOre(e)))
            .filter(_._2 <= CARGO_HOLD)
            .last._1
        println(max_fuel)
    }
}
