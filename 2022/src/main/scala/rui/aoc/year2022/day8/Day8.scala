package rui.aoc.year2022.day8

object Day8 {
  def parseTreeGrid(input: Array[String]): TreeGrid = {
    input.zipWithIndex
      .map {
        case (lineStr, y) => lineStr
          .toCharArray
          .map(_ - '0')
          .zipWithIndex
          .map {
            case (height, x) => Tree(height, x, y)
          }
      }
  }

  type TreeGrid = Array[Array[Tree]]
}
