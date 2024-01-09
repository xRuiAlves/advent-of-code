package day12

class Planet(var pos: Coord3D) {
    var speed = new Coord3D(Array(0, 0, 0))

    def resetSpeed: Unit = {
        speed = new Coord3D(Array(0, 0, 0))
    }

    def updateSpeed(other_planet: Planet): Unit = {
        for (i <- speed.coords.indices) {
            updateSpeed(other_planet, i)
        }
    }

    def updateSpeed(other_planer: Planet, coord: Int): Unit = {
        speed.coords(coord) += math.signum(other_planer.pos.coords(coord) - this.pos.coords(coord))
    }

    def updatePosition: Unit = {
        for (i <- pos.coords.indices) {
            updatePosition(i)
        }
    }

    def updatePosition(coord: Int): Unit = {
        pos.coords(coord) += speed.coords(coord)
    }

    def getEnergy: Int = pos.coords.map(math.abs).sum * speed.coords.map(math.abs).sum

    def getCoordInfo(coord: Int): (Int, Int) = (pos.coords(coord), speed.coords(coord))

    override def toString: String = s"pos=$pos\nspeed=$speed"
}
