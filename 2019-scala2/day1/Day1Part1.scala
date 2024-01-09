package day1

import utils.FileReader

object Day1Part1 {
    def main(args: Array[String]): Unit = {
        val mass_list = FileReader.readFile("src/day1/input.txt").map(_.toInt)
        val total_fuel = mass_list.fold(0)((fuel, mass) => fuel + calcFuel(mass))
        println(total_fuel)
    }

    def calcFuel(mass: Int) : Int = mass / 3 - 2
}
