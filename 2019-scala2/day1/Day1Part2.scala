package day1

import utils.FileReader

object Day1Part2 {
    def main(args: Array[String]): Unit = {
        val mass_list = FileReader.readFile("src/day1/input.txt").map(_.toInt)
        val total_fuel = mass_list.fold(0)((fuel, mass) => fuel + calcDeepFuel(mass))
        println(total_fuel)
    }

    def calcDeepFuel(mass: Int) : Int = {
        var curr_mass : Int = mass
        var total_fuel : Int = 0

        while (curr_mass > 0) {
            val fuel = math.max(0, calcFuel(curr_mass))
            curr_mass = fuel
            total_fuel += fuel
        }

        total_fuel
    }

    def calcFuel(mass: Int) : Int = mass / 3 - 2
}
