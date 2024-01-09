package rui.aoc.year2022.day16

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day16.Day16.{ValveGraph, findBestFlowProduction, parseGraph}
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day16Part2 extends ProblemSolution {
  private final val TIME = 26

  override def solve(): AnyVal = {
    val graph = parseGraph(FileIO.readResourceLines("day16.txt"))
    simplifyGraph(graph)
    findBestFlow(graph)
  }

  private def simplifyGraph(graph: ValveGraph): Unit = {
    graph.values.zipWithIndex.foreach {
      case (valve, i) => valve.id = i
    }
    val initValveId = graph("AA").id
    val swapValve = graph.values.find(_.id == graph.size - 1).get
    graph("AA").id = swapValve.id
    swapValve.id = initValveId
  }

  private def findBestFlow(graph: ValveGraph): Int = {
    val numCombinationValves = graph.size - 1
    val startNodeId = graph.size - 1
    val numIters = math.pow(2, numCombinationValves - 1).toInt
    val valvesById = graph.map {
      case (_, valve) => valve.id -> valve
    }

    (1 until numIters).foldLeft(0) {
      case (best, bitMask) => {
        val myGraphValveIds = mutable.Set[Int]()
        val elphGraphValveIds = mutable.Set[Int]()

        for (i <- 0 until numCombinationValves) {
          if ((bitMask & (1 << i)) != 0) {
            myGraphValveIds.addOne(i)
          } else {
            elphGraphValveIds.addOne(i)
          }
        }

        myGraphValveIds.addOne(startNodeId)
        elphGraphValveIds.addOne(startNodeId)

        if (elphGraphValveIds.size > myGraphValveIds.size) best
        else {
          val myGraph = buildGraph(graph, valvesById, myGraphValveIds)
          val elphGraph = buildGraph(graph, valvesById, elphGraphValveIds)
          math.max(
            best,
            findBestFlowProduction(myGraph, TIME) + findBestFlowProduction(elphGraph, TIME)
          )
        }
      }
    }
  }

  private def buildGraph(graph: ValveGraph, valvesById: Map[Int, Valve], myGraphValveIds: mutable.Set[Int]) = {
    myGraphValveIds.map(id => {
      val valve = valvesById(id)
      valve.name -> valve.copy(neighbors =
        valve.neighbors.filter(neighbor => myGraphValveIds.contains(graph(neighbor.name).id)))
    }).toMap
  }
}
