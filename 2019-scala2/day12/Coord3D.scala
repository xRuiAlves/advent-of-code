package day12

class Coord3D(val coords: Array[Int]) {
    override def toString: String = s"[${coords.mkString(", ")}]"
}
