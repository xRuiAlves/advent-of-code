package rui.aoc.year2022.day8

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day6.Day6.countCharsAtMarker
import rui.aoc.year2022.day8.Day8.{TreeGrid, parseTreeGrid}
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day8Part1 extends ProblemSolution {
  override def solve(): AnyVal = {
    val grid: TreeGrid = parseTreeGrid(FileIO.readResourceLines("day8.txt"))
    val trees = mutable.Set[Tree]()

    // Horizontal
    grid.foreach(treeLine => {
      findVisibleTrees(trees, treeLine)
      findVisibleTrees(trees, treeLine.reverse)
    })

    // Vertical
    grid.transpose.foreach(treeLine => {
      findVisibleTrees(trees, treeLine)
      findVisibleTrees(trees, treeLine.reverse)
    })

    trees.size
  }

  def findVisibleTrees(trees: mutable.Set[Tree], treeLine: Array[Tree]): Unit = treeLine.foldLeft(-1) {
    case (maxHeight, tree) =>
      if (tree.height > maxHeight) {
        trees.addOne(tree)
        tree.height
      }
      else maxHeight
  }
}
