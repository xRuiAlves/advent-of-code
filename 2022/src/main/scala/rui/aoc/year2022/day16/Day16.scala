package rui.aoc.year2022.day16

import scala.collection.mutable

object Day16 {
  private final val START_VALVE_NAME = "AA"

  def parseGraph(valveLines: Array[String]): ValveGraph = {
    val graph = valveLines
      .zipWithIndex
      .map { case (valveStr, i) => Valve.fromStr(valveStr, i) }
      .map { case valve => valve.name -> valve }
      .toMap

    val matrix = floydWarshalMatrix(graph)
    val nonEmptyValves = graph.values.filter(_.flowRate > 0)

    matrix.indices.map(id => {
      val valve = graph.values.find(_.id == id).get
      valve.name -> valve.copy(neighbors = nonEmptyValves.filter(_.id != id).map(neighbor =>
        ValveNeighbor(neighbor.name, matrix(id)(neighbor.id))
      ).to(mutable.Set))
    }).filter {
      case (_, valve) => valve.name.equals("AA") || valve.flowRate > 0
    }.toMap
  }

  private def floydWarshalMatrix(graph: ValveGraph): Array[Array[Int]] = {
    val matrix = Array.fill(graph.size) {
      Array.fill(graph.size) { Int.MaxValue / 2 - 1 }
    }
    matrix.indices.foreach(i => matrix(i)(i) = 0)
    graph.values.foreach(valve => valve.neighbors.foreach(neighbor => {
      matrix(valve.id)(graph(neighbor.name).id) = neighbor.distance
    }))

    for (k <- matrix.indices; i <- matrix.indices; j <- matrix.indices) {
      matrix(i)(j) = math.min(matrix(i)(j), matrix(i)(k) + matrix(k)(j))
    }
    matrix
  }

  def findBestFlowProduction(graph: ValveGraph, time: Int): Int = {
    val cache = mutable.Map[CacheState, Int]()
    findBestFlowProduction(graph, cache, START_VALVE_NAME, time, 0L)
  }

  def findBestFlowProduction(
    graph: ValveGraph,
    cache: mutable.Map[CacheState, Int],
    currValveName: String,
    timeLeft: Int,
    turnedOnValvesBitMap: Long
  ): Int = if (timeLeft == 0) 0 else cache.get(currValveName, timeLeft, turnedOnValvesBitMap) match {
    case Some(cachedVal) => cachedVal
    case None =>
      val valve = graph(currValveName)
      val flowProduction = computeFlowProduction(graph, turnedOnValvesBitMap)

      val validNeighbors = valve.neighbors.filter(neighbor => timeLeft - neighbor.distance >= 0)
      val visitingBestNeighbor =
        if (validNeighbors.isEmpty) 0
        else validNeighbors.map(neighbor => neighbor.distance * flowProduction +
          findBestFlowProduction(graph, cache, neighbor.name, timeLeft - neighbor.distance, turnedOnValvesBitMap)).max

      cache((currValveName, timeLeft, turnedOnValvesBitMap)) = math.max(
        if (valve.flowRate == 0 || isValveTurnedOn(valve, turnedOnValvesBitMap)) visitingBestNeighbor
        else {
          val updatedBipMap = turnedOnValvesBitMap | (1L << valve.id)
          val visitingSelf = flowProduction + findBestFlowProduction(graph, cache, currValveName, timeLeft - 1, updatedBipMap)
          math.max(visitingSelf, visitingBestNeighbor)
        },
        flowProduction * timeLeft
      )
      cache((currValveName, timeLeft, turnedOnValvesBitMap))
  }

  def computeFlowProduction(graph: ValveGraph, bitMap: Long): Int = graph.values.map {
    case valve => if (isValveTurnedOn(valve, bitMap)) {
      valve.flowRate
    } else 0
  }.sum

  def isValveTurnedOn(valve: Valve, turnedOnValvesBitMap: Long): Boolean =
    (turnedOnValvesBitMap & (1L << valve.id)) != 0

  type ValveGraph = Map[String, Valve]
  type CacheState = (String, Int, Long)
}
