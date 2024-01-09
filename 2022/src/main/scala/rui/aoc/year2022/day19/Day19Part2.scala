package rui.aoc.year2022.day19

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day19.Day19.parseBlueprints
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day19Part2 extends ProblemSolution {
  private final val SIMULATION_TIME = 32
  private final val MAX_BRANCHING_FACTOR = 2500000
  private var branchingFactor = 0

  override def solve(): AnyVal = {
    val blueprints = parseBlueprints(FileIO.readResourceLines("day19.txt"))
    blueprints.take(3).map(blueprint => {
      println(blueprint)
      val cache = mutable.Map[BlueprintState, Int]()
      val maxOreProduction = Seq(blueprint.oreOreCost, blueprint.clayOreCost, blueprint.obsidianOreCost, blueprint.geodeOreCost).max
      val maxClayProduction = blueprint.obsidianClayCost
      val maxObsidianProduction = blueprint.geodeObsidianCost
      branchingFactor = 0
      val production = geodeProduction(cache, blueprint, (maxOreProduction, maxClayProduction, maxObsidianProduction), BlueprintState(
          SIMULATION_TIME,
          Production(1, 0),
          Production(0, 0),
          Production(0, 0),
          Production(0, 0)
      ), false, false, false)
      println(production)
      production
    })
    .product
  }

  // However, this BFS is too slow so for part 1, I have one optimization:
  // If the number of geodes in some state plus the amount of time left is less
  // than the maximum number of geodes of all the states visited so far, then cut that state out.
  def geodeProduction(cache: mutable.Map[BlueprintState, Int], blueprint: Blueprint, productionLimits: (Int, Int, Int), state: BlueprintState, skippedOre: Boolean, skippedClay: Boolean, skippedObsidian: Boolean): Int = cache.get(state) match {
    case Some(res) => res
    case None => if (state.timeLeft == 0) state.geodeProduction.amount else {
      branchingFactor += 1
      if (branchingFactor > MAX_BRANCHING_FACTOR) {
        return state.geodeProduction.amount
      }

      val cantBuildGeode = state.oreProduction.amount < blueprint.geodeOreCost || state.obsidianProduction.amount < blueprint.geodeObsidianCost
      val buildGeode = if (cantBuildGeode) 0 else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots - blueprint.geodeOreCost),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots - blueprint.geodeObsidianCost),
        Production(state.geodeProduction.robots + 1, state.geodeProduction.amount + state.geodeProduction.robots)
      ), false, false, false)

      if (buildGeode > 0) {
        cache(state) = buildGeode
        return buildGeode
      }

      val cantBuildOre = skippedOre || state.oreProduction.amount < blueprint.oreOreCost ||
        state.oreProduction.robots >= productionLimits._1
      val buildOre = if (cantBuildOre) 0 else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots + 1, state.oreProduction.amount + state.oreProduction.robots - blueprint.oreOreCost),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ), false, false, false)
      val cantBuildClay = skippedClay || state.oreProduction.amount < blueprint.clayOreCost ||
        state.clayProduction.robots >= productionLimits._2
      val buildClay = if (cantBuildClay) 0 else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots - blueprint.clayOreCost),
        Production(state.clayProduction.robots + 1, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ), false, false, false)
      val cantBuildObsidian = skippedObsidian || (state.oreProduction.amount < blueprint.obsidianOreCost || state.clayProduction.amount < blueprint.obsidianClayCost) ||
        (state.obsidianProduction.robots >= productionLimits._3)
      val buildObsidian = if (cantBuildObsidian) 0 else geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots - blueprint.obsidianOreCost),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots - blueprint.obsidianClayCost),
        Production(state.obsidianProduction.robots + 1, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ), false, false, false)

      val buildNothing = geodeProduction(cache, blueprint, productionLimits, BlueprintState(
        state.timeLeft - 1,
        Production(state.oreProduction.robots, state.oreProduction.amount + state.oreProduction.robots),
        Production(state.clayProduction.robots, state.clayProduction.amount + state.clayProduction.robots),
        Production(state.obsidianProduction.robots, state.obsidianProduction.amount + state.obsidianProduction.robots),
        Production(state.geodeProduction.robots, state.geodeProduction.amount + state.geodeProduction.robots)
      ), !cantBuildOre, !cantBuildClay, !cantBuildObsidian)

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
