package day3

import utils.FileReader

import scala.collection.mutable

object Day3Part2 {
    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day3/input.txt").toArray
        val wire1_path = file_lines(0).split(",")
        val wire2_path = file_lines(1).split(",")
        val visited_points = new mutable.HashMap[StepsPoint, StepsPoint]()

        var x : Int = 0
        var y : Int = 0
        var taken_steps : Int = 0

        for (part <- wire1_path) {
            val num_steps = part.substring(1).toInt

            if (part(0) == 'R') {
                for (_ <- 0 until num_steps) {
                    x += 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    if (!visited_points.contains(point)) {
                        visited_points.put(point, point)
                    }
                }
            } else if (part(0) == 'L') {
                for (_ <- 0 until num_steps) {
                    x -= 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    if (!visited_points.contains(point)) {
                        visited_points.put(point, point)
                    }
                }
            } else if (part(0) == 'U') {
                for (_ <- 0 until num_steps) {
                    y += 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    if (!visited_points.contains(point)) {
                        visited_points.put(point, point)
                    }
                }
            } else {
                for (_ <- 0 until num_steps) {
                    y -= 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    if (!visited_points.contains(point)) {
                        visited_points.put(point, point)
                    }
                }
            }
        }

        var best_distance : Int = Int.MaxValue
        x = 0
        y = 0
        taken_steps = 0

        for (part <- wire2_path) {
            val num_steps = part.substring(1).toInt

            if (part(0) == 'R') {
                for (_ <- 0 until num_steps) {
                    x += 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    val collision_point = visited_points.getOrElse(point, null)
                    if (collision_point != null) {
                        best_distance = math.min(best_distance, taken_steps + collision_point.taken_steps)
                    }
                }
            } else if (part(0) == 'L') {
                for (_ <- 0 until num_steps) {
                    x -= 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    val collision_point = visited_points.getOrElse(point, null)
                    if (collision_point != null) {
                        best_distance = math.min(best_distance, taken_steps + collision_point.taken_steps)
                    }
                }
            } else if (part(0) == 'U') {
                for (_ <- 0 until num_steps) {
                    y += 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    val collision_point = visited_points.getOrElse(point, null)
                    if (collision_point != null) {
                        best_distance = math.min(best_distance, taken_steps + collision_point.taken_steps)
                    }
                }
            } else {
                for (_ <- 0 until num_steps) {
                    y -= 1
                    taken_steps += 1
                    val point = new StepsPoint(x, y, taken_steps)
                    val collision_point = visited_points.getOrElse(point, null)
                    if (collision_point != null) {
                        best_distance = math.min(best_distance, taken_steps + collision_point.taken_steps)
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
