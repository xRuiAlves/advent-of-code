package rui.aoc.year2022.day19

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day19.Day19.parseBlueprints
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day19Part1 extends ProblemSolution {
  private final val SIMULATION_TIME = 24

  override def solve(): AnyVal = {
    val blueprints = parseBlueprints(FileIO.readResourceLines("day19.txt"))
    blueprints.map(blueprint => {
      println(blueprint)
      val cache = mutable.Map[BlueprintState, Int]()
      val maxOreProduction = Seq(blueprint.oreOreCost, blueprint.clayOreCost, blueprint.obsidianOreCost, blueprint.geodeOreCost).max
      val maxClayProduction = blueprint.obsidianClayCost
      val maxObsidianProduction = blueprint.geodeObsidianCost
      val production = geodeProduction(cache, blueprint, (maxOreProduction, maxClayProduction, maxObsidianProduction), BlueprintState(
          SIMULATION_TIME,
          Production(1, 0),
          Production(0, 0),
          Production(0, 0),
          Production(0, 0)
      ))
      production * blueprint.id
    })
    .sum
  }

  def geodeProduction(cache: mutable.Map[BlueprintState, Int], blueprint: Blueprint, productionLimits: (Int, Int, Int), state: BlueprintState): Int = cache.get(state) match {
    case Some(res) => res
    case None => if (state.timeLeft == 0) state.geodeProduction.amount else {
      val buildNothing = geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ))
      val buildOre = if (state.oreProduction.amount < blueprint.oreOreCost) 0
      else if (state.oreProduction.robots >= productionLimits._1) 0
      else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots + 1, state.oreProduction.amount + state.oreProduction.robots - blueprint.oreOreCost),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ))
      val buildClay = if (state.oreProduction.amount < blueprint.clayOreCost) 0
      else if (state.clayProduction.robots >= productionLimits._2) 0
      else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots - blueprint.clayOreCost),
        Production(state.clayProduction.robots + 1, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ))
      val buildObsidian = if (state.oreProduction.amount < blueprint.obsidianOreCost || state.clayProduction.amount < blueprint.obsidianClayCost) 0
      else if (state.obsidianProduction.robots >= productionLimits._3) 0
      else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots - blueprint.obsidianOreCost),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots - blueprint.obsidianClayCost),
        Production(state.obsidianProduction.robots + 1, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ))
      val buildGeode = if (state.oreProduction.amount < blueprint.geodeOreCost || state.obsidianProduction.amount < blueprint.geodeObsidianCost) 0 else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots - blueprint.geodeOreCost),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots - blueprint.geodeObsidianCost),
        Production(state.geodeProduction.robots + 1, state.geodeProduction.amount + state.geodeProduction.robots)
      ))

      cache(state) = Seq(
        buildNothing,
        buildOre,
        buildClay,
        buildObsidian,
        buildGeode
      ).max
      cache(state)
    }
  }
}
