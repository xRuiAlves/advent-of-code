package day6

import utils.FileReader

import scala.collection.mutable._

object Day6Part1 {
    final val STARTING_PLACE = "COM"

    def main(args: Array[String]): Unit = {
        val raw_map = FileReader.readFile("src/day6/input.txt").toArray
        val map = new HashMap[String, ArrayBuffer[String]]()

        raw_map.foreach(entry => {
            val objects : Array[String] = entry.split("\\)")

            map(objects(0)) = map.getOrElse(objects(0), new ArrayBuffer[String]()).addOne(objects(1))
        })

        var level = 0
        var num_orbits = 0
        val queue = new Queue[String]()
        queue += STARTING_PLACE

        while (queue.nonEmpty) {
            val level_size = queue.size

            for (i <- 0 until level_size) {
                num_orbits += level
                val curr = queue.dequeue

                for (neighbor <- map.getOrElse(curr, new ArrayBuffer[String]())) {
                    queue += neighbor
                }
            }

            level += 1
        }

        println(num_orbits)
    }
}
