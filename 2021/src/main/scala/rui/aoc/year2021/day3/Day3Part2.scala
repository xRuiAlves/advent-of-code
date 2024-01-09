package rui.aoc.year2021.day3

import rui.aoc.year2021.ProblemSolution
import rui.aoc.year2021.utils.FileIO

class Day3Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val numbers = FileIO.readResourceLines("day3.txt")

    numbers
      .head
      .indices
      .foldLeft((numbers, numbers)) {
        case ((oxygenNumbers, co2Numbers), i) => {
          val oxygenOccurences = countBitOccurrences(oxygenNumbers, i)
          val co2Occurences = countBitOccurrences(co2Numbers, i)
          (
            if (oxygenNumbers.length > 1)
              oxygenNumbers.filter(number => number(i) == (if (oxygenOccurences >= 0) '1' else '0'))
            else
              oxygenNumbers,
            if (co2Numbers.length > 1)
              co2Numbers.filter(number => number(i) == (if (co2Occurences >= 0) '0' else '1'))
            else
              co2Numbers
          )
        }
      } match {
      case (Array(oxygenNumber), Array(co2Number)) =>
        Integer.parseInt(oxygenNumber, 2) * Integer.parseInt(co2Number, 2)
    }
  }

  def countBitOccurrences(numbers: Array[String], index: Int): Int = numbers.foldLeft(0) {
    case (acc, number) => acc + (if (number(index) == '1') 1 else -1)
  }
}
