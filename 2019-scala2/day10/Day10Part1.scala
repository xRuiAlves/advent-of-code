package day10

import utils.FileReader

import scala.collection.mutable

object Day10Part1 {
    final val ASTEROID = '#';

    def main(args: Array[String]): Unit = {
        val map = FileReader.readFile("src/day10/input.txt").map(_.toCharArray).toArray

        var max_detected_asteroids = 0
        for (y <- map.indices; x <- map(y).indices) {
            if (isAsteroid(map(y)(x))) max_detected_asteroids = math.max(
                max_detected_asteroids,
                countDetectedAsteroids(map, x, y)
            )
        }

        println(max_detected_asteroids)
    }

    def isAsteroid(c: Char): Boolean = c == ASTEROID

    def countDetectedAsteroids(map: Array[Array[Char]], x1: Int, y1: Int): Int = {
        val trajectories = new mutable.HashSet[TrajectoryLine]()
        for (y2 <- map.indices; x2 <- map(y2).indices) {
            if (isAsteroid(map(y2)(x2)) && (x1, y1) != (x2, y2)) {
                trajectories += new TrajectoryLine(x1 - x2, y1 - y2)
            }
        }
        trajectories.size
    }
}
