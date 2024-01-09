package rui.aoc.year2022.day8

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day8.Day8.{TreeGrid, parseTreeGrid}
import rui.aoc.year2022.utils.FileIO

import scala.collection.mutable

class Day8Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val grid: TreeGrid = parseTreeGrid(FileIO.readResourceLines("day8.txt"))
    val transposedGrid: TreeGrid = grid.transpose

    grid.map(
      _.map(tree => getScore(grid, transposedGrid, tree)).max
    ).max
  }

  def getScore(grid: TreeGrid, transposedGrid: TreeGrid, tree: Tree): Int = {
    val rightScore = countVisibleTreesInLine(grid(tree.y).drop(tree.x + 1), tree.height)
    val leftScore =  countVisibleTreesInLine(grid(tree.y).take(tree.x).reverse, tree.height)
    val topScore =  countVisibleTreesInLine(transposedGrid.apply(tree.x).drop(tree.y + 1), tree.height)
    val bottomScore =  countVisibleTreesInLine(transposedGrid.apply(tree.x).take(tree.y).reverse, tree.height)
    rightScore * leftScore * topScore * bottomScore
  }

  def countVisibleTreesInLine(treeLine: Array[Tree], baseTreeHeight: Int): Int = math.min(
    treeLine.takeWhile(_.height < baseTreeHeight).length + 1,
    treeLine.length
  )
}
