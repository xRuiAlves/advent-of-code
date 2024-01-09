package rui.aoc.year2022.day19

case class Blueprint(
  id: Int,
  oreOreCost: Int,
  clayOreCost: Int,
  obsidianOreCost: Int,
  obsidianClayCost: Int,
  geodeOreCost: Int,
  geodeObsidianCost: Int
)

object Blueprint {
  private final val BLUEPRINT_REGEX = "Blueprint (?<id>\\d+): Each ore robot costs (?<oreOreCost>\\d+) ore. Each clay robot costs (?<clayOreCost>\\d+) ore. Each obsidian robot costs (?<obsidianOreCost>\\d+) ore and (?<obsidianClayCost>\\d+) clay. Each geode robot costs (?<geodeOreCost>\\d+) ore and (?<geodeObsidianCost>\\d+) obsidian.".r

  def apply(blueprintStr: String): Blueprint = {
    val matches = BLUEPRINT_REGEX.findFirstMatchIn(blueprintStr).get
    Blueprint(
      matches.group("id").toInt,
      matches.group("oreOreCost").toInt,
      matches.group("clayOreCost").toInt,
      matches.group("obsidianOreCost").toInt,
      matches.group("obsidianClayCost").toInt,
      matches.group("geodeOreCost").toInt,
      matches.group("geodeObsidianCost").toInt
    )
  }
}



