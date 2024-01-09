package rui.aoc.year2022.day19

object Day19 {
  def parseBlueprints(blueprintLines: Array[String]): Array[Blueprint] = blueprintLines
    .map(Blueprint.apply)
}
