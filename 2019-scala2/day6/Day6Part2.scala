package day6

import utils.FileReader

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Day6Part2 {
    final val STARTING_PLACE1 = "YOU"
    final val STARTING_PLACE2 = "SAN"
    final val TARGET_PLACE = "COM"

    def main(args: Array[String]): Unit = {
        val raw_map = FileReader.readFile("src/day6/input.txt").toArray
        val map = new mutable.HashMap[String, String]()

        raw_map.foreach(entry => {
            val objects: Array[String] = entry.split("\\)")

            map(objects(1)) = objects(0)
        })

        val you_path: ArrayBuffer[String] = buildPath(STARTING_PLACE1, TARGET_PLACE, map)
        val san_path: ArrayBuffer[String] = buildPath(STARTING_PLACE2, TARGET_PLACE, map)
        val collision_node = findPathCollision(you_path, san_path)
        val distance = you_path.lastIndexOf(collision_node) + san_path.lastIndexOf(collision_node) - 2

        println(distance)
    }

    def buildPath(start: String, target: String, map: mutable.HashMap[String, String]): ArrayBuffer[String] = {
        val path = new ArrayBuffer[String]()
        path += start
        var curr = start

        while (curr != target) {
            curr = map(curr)
            path += curr
        }

        path
    }

    def findPathCollision(path1: ArrayBuffer[String], path2: ArrayBuffer[String]): String = {
        val path2_nodes : Set[String] = path2.toSet

        for (node <- path1) {
            if (path2_nodes.contains(node)) {
                return node
            }
        }

        throw new Exception("No collision found")
    }
}
