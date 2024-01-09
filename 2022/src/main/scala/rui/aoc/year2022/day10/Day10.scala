package rui.aoc.year2022.day10

object Day10 {
  def computeCycleValues(instructions: Array[String]): Vector[Int] =  instructions
    .foldLeft(Vector(1))((values, instruction) => instruction match {
      case "noop" => values.appended(values.last)
      case _ => instruction.split(" ") match {
        case Array(_, delta) => values.appended(values.last).appended(values.last + delta.toInt)
      }
    })
}
