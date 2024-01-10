//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines

import scala.annotation.tailrec
import scala.collection.mutable

object Day24 {
  private[this] final val weaknessesPattern = "weak to (?<weaknesses>\\w+(, \\w+)*)".r
  private[this] final val immunitiesPattern = "immune to (?<immunities>\\w+(, \\w+)*)".r

  def main(args: Array[String]): Unit = {
    val input = readResourceLines("day24.txt")
    val armiesMap = input
      .mkString("\n")
      .split("\n\n")
      .map(parseArmy)
      .map(army => army.name -> army)
      .toMap

    val part1 = updateArmies(armiesMap).armyResults.map(_.units).max
    val part2 = 0

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  case class Army(groups: Map[Int, Group], name: String)

  case class Group(
    units: Int,
    hp: Int,
    weaknesses: Set[String],
    immunities: Set[String],
    attackType: String,
    attackDamage: Int,
    initiative: Int
  ) {
    lazy final val effectivePower: Int = units * attackDamage
  }

  def parseArmy(armyStr: String): Army = Army(armyStr
    .split("\n")
    .drop(1)
    .map(parseGroup)
    .zipWithIndex
    .map { case (group, i) => (i + 1, group) }
    .toMap, armyStr.takeWhile(_ != ':'))

  def parseGroup(groupStr: String): Group = groupStr match
    case s"$units units each with $hp hit points ${modifiers}with an attack that does $attackDamage $attackType damage at initiative $initiative" =>
      val weaknesses = weaknessesPattern.findFirstMatchIn(modifiers).map(_.group("weaknesses")).getOrElse("").split(", ").toSet
      val immunities = immunitiesPattern.findFirstMatchIn(modifiers).map(_.group("immunities")).getOrElse("").split(", ").toSet
      Group(units.toInt, hp.toInt, weaknesses, immunities, attackType, attackDamage.toInt, initiative.toInt)

  def getDamage(attackingGroup: Group, defendingGroup: Group): Int = {
    def modifier =
      if (defendingGroup.immunities.contains(attackingGroup.attackType)) 0
      else if (defendingGroup.weaknesses.contains(attackingGroup.attackType)) 2
      else 1
    modifier * attackingGroup.effectivePower
  }

  def attack(attackingGroup: Group, defendingGroup: Group): Group = defendingGroup.copy(
    units = math.max(0, math.ceil((defendingGroup.units * defendingGroup.hp - getDamage(attackingGroup, defendingGroup)) / defendingGroup.hp.toDouble).toInt)
  )

  case class Selection(
    attackingGroupId: Int,
    attackingGroupArmy: String,
    defendingGroupId: Int,
    defendingGroupArmy: String
  )

  def selection(attackingArmy: Army, defendingArmy: Army): Array[Selection] = {
    val selections = mutable.ArrayBuffer[Selection]()

    attackingArmy.groups.toArray.sortWith(selectionChoosingOrder).foreach(attackingGroup => {
      def selectionAttackingOrder(group1: (Int, Group), group2: (Int, Group)): Boolean = {
        val damageTo1 = getDamage(attackingGroup._2, group1._2)
        val damageTo2 = getDamage(attackingGroup._2, group2._2)

        if (damageTo1 != damageTo2) damageTo2 < damageTo1
        else selectionChoosingOrder(group1, group2)
      }

      val maybeDefendingGroup = defendingArmy
        .groups
        .toArray
        .filter(group => !selections.map(_.defendingGroupId).contains(group._1))
        .sortWith(selectionAttackingOrder)
        .headOption

      maybeDefendingGroup match {
        case Some(defendingGroup) => selections.addOne(Selection(attackingGroup._1, attackingArmy.name, defendingGroup._1, defendingArmy.name))
        case None =>
      }
    })

    selections.toArray
  }

  def attacks(armiesMap: Map[String, Army]): Array[Selection] = {
    val armies = armiesMap.values
    val army1 = armies.head
    val army2 = armies.last
    selection(army2, army1)
      .concat(selection(army1, army2))
      .sortBy(selection => armiesMap(selection.attackingGroupArmy).groups(selection.attackingGroupId).initiative)
      .reverse
  }

  def attackRound(armiesMap: Map[String, Army]): Map[String, Army] = attacks(armiesMap).foldLeft(armiesMap) {
    case (currArmies, selection) =>
      val attackingGroup = currArmies(selection.attackingGroupArmy).groups.get(selection.attackingGroupId)
      val defendingGroup = currArmies(selection.defendingGroupArmy).groups.get(selection.defendingGroupId)

      if (attackingGroup.isEmpty || defendingGroup.isEmpty) currArmies
      else {
        val defendingGroupAfterAttack = attack(attackingGroup.get, defendingGroup.get)
        val updatedGroups =
          if (defendingGroupAfterAttack.units != 0) currArmies(selection.defendingGroupArmy).groups.updated(
            selection.defendingGroupId,
            defendingGroupAfterAttack
          )
          else currArmies(selection.defendingGroupArmy).groups.removed(selection.defendingGroupId)

        currArmies.updated(
          selection.defendingGroupArmy,
          currArmies(selection.defendingGroupArmy).copy(groups = updatedGroups)
        )
      }
  }

  def selectionChoosingOrder(group1: (Int, Group), group2: (Int, Group)): Boolean = {
    if (group2._2.effectivePower != group1._2.effectivePower) group2._2.effectivePower < group1._2.effectivePower
    else group2._2.initiative < group1._2.initiative
  }

  case class ArmyResult(name: String, units: Int)
  case class BattleResult(armyResults: Array[ArmyResult])

  def getBattleResult(armiesMap: Map[String, Army]): BattleResult = BattleResult(armiesMap.map {
    case (name, army) => ArmyResult(name, army.groups.map(_._2.units).sum)
  }.toArray)

  @tailrec
  def updateArmies(currArmies: Map[String, Army]): BattleResult = {
    val battleResult = getBattleResult(currArmies)
    if (battleResult.armyResults.map(_.units).contains(0)) battleResult
    else updateArmies(attackRound(currArmies))
  }
}