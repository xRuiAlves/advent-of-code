package rui.aoc.year2022.day18

object Day18 {
  def parseCoords(coordLines: Array[String]): Set[Coord3D] = coordLines
    .map(_.split(",").map(_.toInt) match {
      case Array(x, y, z) => (x, y, z)
    })
    .toSet

  def adjacentCoords(cube: Coord3D): Set[Coord3D] = Set(
    (cube._1 - 1, cube._2, cube._3),
    (cube._1 + 1, cube._2, cube._3),
    (cube._1, cube._2 + 1, cube._3),
    (cube._1, cube._2 - 1, cube._3),
    (cube._1, cube._2, cube._3 + 1),
    (cube._1, cube._2, cube._3 - 1)
  )


  type Coord3D = (Int, Int, Int)
}

