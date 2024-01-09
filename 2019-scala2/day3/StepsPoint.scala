package day3

class StepsPoint(override val x: Int, override val y: Int, val taken_steps: Int) extends Point(x, y) {
    override def toString: String = s"[$x, $y]: $taken_steps"
}
