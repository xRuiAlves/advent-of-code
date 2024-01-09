package day10

object PI {
    final val value = 2 * math.acos(0)
}

object VerticalVector {
    final val x = 0
    final val y = 1
    final val modulus: Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))
}

class TrajectoryLine(var x: Int, var y: Int, val pos: (Int, Int) = null) {
    val modulus: Double = math.sqrt(math.pow(x, 2) + math.pow(y, 2))

    val angle: Double = {
        val n = x * VerticalVector.x + y * VerticalVector.y
        val d = modulus * VerticalVector.modulus
        val raw_angle = math.atan2(
            VerticalVector.x * y - VerticalVector.y * x,
            VerticalVector.x * x + VerticalVector.y * y
        )

        if (raw_angle >= 0) raw_angle else 2 * PI.value + raw_angle
    }

    (x, y) match {
        case (0, _) => y = 1 * math.signum(y)
        case (_, 0) => x = 1 * math.signum(x)
        case (x, y) => {
            var divisor = gcd(x, y)
            this.x /= divisor
            this.y /= divisor
        }
    }

    private def gcd(a: Int, b: Int): Int = {
        for (i <- math.min(math.abs(x), math.abs(y)) to 2 by -1) {
            if ((a % i == 0) && (b % i == 0)) return i
        }
        1
    }

    override def toString: String = s"$x/$y"

    override def hashCode(): Int = ((x*1000 + y << 2) / 7) % 13

    override def equals(obj: Any): Boolean = obj match {
        case obj: TrajectoryLine => obj.x == x && obj.y == y
        case _ => false
    }
}
