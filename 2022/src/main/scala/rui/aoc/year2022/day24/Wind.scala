package rui.aoc.year2022.day24

trait Wind {
  def update: Wind
  def x: Int
  def y: Int
}

case class RightWind(x: Int, y: Int, maxX: Int) extends Wind {
  override def update: Wind = RightWind(if (x == maxX) 1 else x + 1, y, maxX)
}

case class LeftWind(x: Int, y: Int, maxX: Int) extends Wind {
  override def update: Wind = LeftWind(if (x == 1) maxX else x - 1, y, maxX)
}

case class DownWind(x: Int, y: Int, maxY: Int) extends Wind {
  override def update: Wind = DownWind(x, if (y == maxY) 1 else y + 1, maxY)
}

case class UpWind(x: Int, y: Int, maxY: Int) extends Wind {
  override def update: Wind = UpWind(x, if (y == 1) maxY else y - 1, maxY)
}
