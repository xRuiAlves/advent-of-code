package day12

import utils.FileReader

object Day12Part1 {
    final val NUM_STEPS = 1000
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

        for (step <- 0 until NUM_STEPS) {
            updatePlanetsSpeed(planets)
            planets.foreach(_.updatePosition)
        }

        val total_energy = planets.map(planet => planet.getEnergy).sum
        println(total_energy)

    }

    def updatePlanetsSpeed(planets: Array[Planet]): Unit = {
        for (planet_idx <- planets.indices; other_planet_idx <- planets.indices) {
            if (planet_idx != other_planet_idx) {
                planets(planet_idx).updateSpeed(planets(other_planet_idx))
            }
        }
    }
}
