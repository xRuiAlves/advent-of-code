package rui.aoc.year2021.day22

import rui.aoc.year2021.day22.Cube.simplify

case class Cube(x0: Long, x1: Long, y0: Long, y1: Long, z0: Long, z1: Long) {
  final lazy val isValid = x1 >= x0 && y1 >= y0 && z1 >= z0
  final lazy val volume = (x1 - x0 + 1) * (y1 - y0 + 1) * (z1 - z0 + 1)

  def intersection(other: Cube): Option[Cube] = {
    val intersection = Cube(
      math.max(this.x0, other.x0),
      math.min(this.x1, other.x1),
      math.max(this.y0, other.y0),
      math.min(this.y1, other.y1),
      math.max(this.z0, other.z0),
      math.min(this.z1, other.z1)
    )

    if (intersection.isValid) Option(intersection)
    else Option.empty
  }

  def split(intersectionCube: Cube): Set[Cube] = {
    Set(
      Cube(x0, intersectionCube.x0 - 1, y0, y1, z0, z1),
      Cube(intersectionCube.x1 + 1, x1, y0, y1, z0, z1),
      Cube(x0, x1, y0, intersectionCube.y0 - 1, z0, z1),
      Cube(x0, x1, intersectionCube.y1 + 1, y1, z0, z1),
      Cube(x0, x1, y0, y1, z0, intersectionCube.z0 - 1),
      Cube(x0, x1, y0, y1, intersectionCube.z1 + 1, z1)
    ).filter(_.isValid)
  }

  def subtract(other: Cube): Set[Cube] = {
    intersection(other) match {
      case None => Set(this)
      case Some(intersectionCube) => simplify(this.split(intersectionCube))
    }
  }
}

object Cube {
  def simplify(cubes: Set[Cube]): Set[Cube] = {
    cubes.foreach(cubeA => {
      cubes.foreach(cubeB => {
        if (cubeA != cubeB) {
          val intersectionCube = cubeA.intersection(cubeB)
          if (intersectionCube.nonEmpty) {
            return simplify(cubes.excl(cubeA).concat(cubeA.split(intersectionCube.get)))
          }
        }
      })
    })
    cubes
  }
}
