package day10

import utils.FileReader

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object Day10Part2 {
    final val ASTEROID = '#';
    final val TARGET_ASTEROID = 200

    def main(args: Array[String]): Unit = {
        val map = FileReader.readFile("src/day10/input.txt").map(_.toCharArray).toArray
        val best_place = findBestPlace(map)
        val last_destroyed_asteroid = destroyAsteroids(map, best_place)
        println(hashPosition(last_destroyed_asteroid))
    }

    def findBestPlace(map: Array[Array[Char]]): (Int, Int) = {
        var max_detected_asteroids = 0
        var best_place = (0, 0)
        for (y <- map.indices; x <- map(y).indices) {

            if (isAsteroid(map(y)(x))) {
                val num_asteroids = countDetectedAsteroids(map, x, y)
                if (num_asteroids > max_detected_asteroids) {
                    max_detected_asteroids = num_asteroids
                    best_place = (x, y)
                }
            }
        }
        best_place
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

    def findTrajectoryLines(map: Array[Array[Char]], x1: Int, y1: Int): mutable.HashMap[TrajectoryLine, ListBuffer[TrajectoryLine]] = {
        val trajectory_lines = new mutable.HashMap[TrajectoryLine, ListBuffer[TrajectoryLine]]()

        for (y2 <- map.indices; x2 <- map(y2).indices) {
            if (isAsteroid(map(y2)(x2)) && (x1, y1) != (x2, y2)) {
                val trajectory = new TrajectoryLine(x1 - x2, y1 - y2, (x2, y2))

                if (!trajectory_lines.contains(trajectory)) {
                    trajectory_lines(trajectory) = new ListBuffer[TrajectoryLine]
                }
                trajectory_lines(trajectory) += trajectory
            }
        }
        trajectory_lines
    }

    def destroyAsteroids(map: Array[Array[Char]], best_place: (Int, Int)): (Int, Int) = {
        val sorted_trajectories = findTrajectoryLines(map, best_place._1, best_place._2)
            .toList
            .sortWith((l, r) => l._1.angle < r._1.angle)
            .map(elem => elem._2.sortWith(
                (l, r) => l.modulus < r.modulus
            ))

        var progress = true
        var destroyed_count = 0

        while (progress) {
            progress = false

            for (line <- sorted_trajectories) {
                if (line.nonEmpty) {
                    destroyed_count += 1
                    progress = true
                    val asteroid_position = line.remove(0).pos
                    if (destroyed_count == TARGET_ASTEROID) return asteroid_position
                }
            }
        }

        throw new Exception(s"Solution not found, less than $TARGET_ASTEROID asteroids exist!")
    }

    def hashPosition(pos: (Int, Int)): Int = pos._1 * 100 + pos._2
}
