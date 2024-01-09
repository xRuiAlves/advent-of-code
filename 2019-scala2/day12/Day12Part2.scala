package day12

import utils.FileReader

import scala.collection.mutable

object Day12Part2 {
    final val PLANET_REGEX_PATTERN = "<x=(-?\\d+), y=(-?\\d+), z=(-?\\d+)>".r

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day12/input.txt").toArray

        val planets = file_lines.map(line => {
            val results = PLANET_REGEX_PATTERN.findAllMatchIn(line).toList
            new Planet(new Coord3D(Array(
                results.head.group(1).toInt,
                results.head.group(2).toInt,
                results.head.group(3).toInt
            )))
        })

        val num_steps = lcm(
            simulateCoord(planets, 0),
            simulateCoord(planets, 1),
            simulateCoord(planets, 2)
        )

        println(num_steps)
    }

    def simulateCoord(planets: Array[Planet], coord: Int): Int = {
        val visited = new mutable.HashSet[((Int, Int), (Int, Int), (Int, Int), (Int, Int))]()
        while (visitPlanetsPosition(planets, coord, visited)) {
            updatePlanetsSpeed(planets, coord)
            planets.foreach(planet => planet.updatePosition(coord))
        }

        visited.size
    }

    def visitPlanetsPosition(planets: Array[Planet], coord: Int, visited: mutable.HashSet[((Int, Int), (Int, Int), (Int, Int), (Int, Int))]): Boolean = visited.add((
        planets(0).getCoordInfo(coord),
        planets(1).getCoordInfo(coord),
        planets(2).getCoordInfo(coord),
        planets(3).getCoordInfo(coord)
    ))

    def updatePlanetsSpeed(planets: Array[Planet], coord: Int): Unit = {
        for (planet_idx <- planets.indices; other_planet_idx <- planets.indices) {
            if (planet_idx != other_planet_idx) {
                planets(planet_idx).updateSpeed(planets(other_planet_idx), coord)
            }
        }
    }

    def lcm(n1: Long, n2: Long, n3: Long): Long = lcm(lcm(n1, n2), n3)

    def lcm(n1: Long, n2: Long): Long = {
        var curr = math.max(n1, n2)
        val step = curr

        while (true) {
            if (curr % n1 == 0 && curr % n2 == 0) return curr
            else curr += step
        }
        curr
    }
}
