package day3

import utils.FileReader

import scala.collection.mutable

object Day3Part1 {
    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day3/input.txt").toArray
        val wire1_path = file_lines(0).split(",")
        val wire2_path = file_lines(1).split(",")
        val visited_points = new mutable.HashSet[Point]

        var x : Int = 0
        var y : Int = 0

        for (part <- wire1_path) {
            val num_steps = part.substring(1).toInt

            if (part(0) == 'R') {
                for (_ <- 0 until num_steps) {
                    x += 1
                    visited_points.add(new Point(x, y))
                }
            } else if (part(0) == 'L') {
                for (_ <- 0 until num_steps) {
                    x -= 1
                    visited_points.add(new Point(x, y))
                }
            } else if (part(0) == 'U') {
                for (_ <- 0 until num_steps) {
                    y += 1
                    visited_points.add(new Point(x, y))
                }
            } else {
                for (_ <- 0 until num_steps) {
                    y -= 1
                    visited_points.add(new Point(x, y))
                }
            }
        }

        var best_distance : Int = Int.MaxValue
        x = 0
        y = 0

        for (part <- wire2_path) {
            val num_steps = part.substring(1).toInt

            if (part(0) == 'R') {
                for (_ <- 0 until num_steps) {
                    x += 1
                    val point = new Point(x, y)
                    if (visited_points.contains(point)) {
                        best_distance = math.min(best_distance, point.getManhattanDistance)
                    }
                }
            } else if (part(0) == 'L') {
                for (_ <- 0 until num_steps) {
                    x -= 1
                    val point = new Point(x, y)
                    if (visited_points.contains(point)) {
                        best_distance = math.min(best_distance, point.getManhattanDistance)
                    }
                }
            } else if (part(0) == 'U') {
                for (_ <- 0 until num_steps) {
                    y += 1
                    val point = new Point(x, y)
                    if (visited_points.contains(point)) {
                        best_distance = math.min(best_distance, point.getManhattanDistance)
                    }
                }
            } else {
                for (_ <- 0 until num_steps) {
                    y -= 1
                    val point = new Point(x, y)
                    if (visited_points.contains(point)) {
                        best_distance = math.min(best_distance, point.getManhattanDistance)
                    }
                }
            }
        }

        if (best_distance == Int.MaxValue) {
            throw new Exception("Answer not found")
        }

        println(best_distance)
    }
}
