package rui.aoc.year2022.day22

case class State(x: Int, y: Int, dir: Int) {
  def turn(turningDir: Char): State = State(x, y, turningDir match {
    case 'R' => (dir + 1) % 4
    case 'L' =>
      if (dir == 0) 3
      else dir - 1
  })
}
