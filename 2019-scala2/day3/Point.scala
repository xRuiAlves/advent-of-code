package day3

class Point(val x: Int, val y: Int) {
    override def equals(obj: Any): Boolean =
        obj.asInstanceOf[Point].x == x &&
            obj.asInstanceOf[Point].y == y

    override def hashCode(): Int = getManhattanDistance

    def getManhattanDistance : Int = math.abs(x) + math.abs(y)

    override def toString: String = s"[$x, $y]"
}
