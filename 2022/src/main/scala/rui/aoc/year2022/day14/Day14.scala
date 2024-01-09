package rui.aoc.year2022.day14

object Day14 {
  def parseGridInstructions(rawInstructions: Array[String]): Array[Array[Coordinate]] = rawInstructions.map(_
    .split(" -> ")
    .map(_.split(",") match {
      case Array(x, y) => (x.toInt, y.toInt)
    }))

  def buildGrid(gridWidth: Int, gridHeight: Int): Grid = Array.fill(gridHeight) {
    Array.fill(gridWidth) { Empty.value }
  }

  def drawLine(grid: Grid, from: Coordinate, to: Coordinate): Unit = {
    for (i <- math.min(from._2, to._2) to math.max(from._2, to._2);
         j <- math.min(from._1, to._1) to math.max(from._1, to._1)) {
      grid(i)(j) = Wall.value
    }
  }

  def drawLines(grid: Grid, gridInstructions: Array[Array[(Int, Int)]]): Unit = gridInstructions.foreach(coordsLine => {
    coordsLine.sliding(2).foreach {
      case Array(from, to) => drawLine(grid, from, to)
    }
  })

  def maybeDropSand(grid: Grid, isAllSandDropped: Coordinate => Boolean = _ => true): Boolean = {
    var sandX = SAND_SOURCE_POSITION._1
    var sandY = SAND_SOURCE_POSITION._2

    while (sandY < grid.length - 1) {
      if (grid(sandY + 1)(sandX) == Empty.value) {
        sandY += 1
      }
      else if (grid(sandY + 1)(sandX - 1) == Empty.value) {
        sandY += 1
        sandX -= 1
      }
      else if (grid(sandY + 1)(sandX + 1) == Empty.value) {
        sandY += 1
        sandX += 1
      }
      else {
        grid(sandY)(sandX) = Sand.value
        return !isAllSandDropped((sandX, sandY))
      }
    }
    true
  }

  type Grid = Array[Array[Char]]
  type Coordinate = (Int, Int)

  final val SAND_SOURCE_POSITION = (500, 0)

  sealed trait GridCell {
    def value: Char
  }

  case object Wall extends GridCell {
    override def value: Char = '#'
  }

  case object Empty extends GridCell {
    override def value: Char = '.'
  }

  case object Sand extends GridCell {
    override def value: Char = 'o'
  }
}
