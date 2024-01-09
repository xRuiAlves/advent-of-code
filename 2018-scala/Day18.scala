//> using scala "3.3.0"
//> using jvm "temurin:17"
//> using file util/ResourceUtils.scala
//> using resourceDir inputs

import util.ResourceUtils.readResourceLines
import scala.collection.mutable
import scala.annotation.tailrec

object Day18 {
  type Matrix2D = Array[Array[Char]]

  private[this] final val OpenGround = '.'
  private[this] final val Tree = '|'
  private[this] final val Lumberyard = '#'

  private[this] final val Part1Generations = 10
  private[this] final val Part2Generations = 1000000000

  def main(args: Array[String]): Unit = {
    val matrix: Matrix2D = readResourceLines("day18.txt")
      .map(line => line.toCharArray)

    lazy val part1 = countMatrixScore(generate(matrix, Part1Generations))
    lazy val part2 = {
      val (period, delta0) = calcMatrixPeriod(matrix)
      val simplifiedNumGenerations = delta0 + ((Part2Generations - delta0) % period)
      countMatrixScore(generate(matrix, simplifiedNumGenerations))
    }

    println(s"Part 1: $part1")
    println(s"Part 2: $part2")
  }

  def copyMatrix2D(matrix: Matrix2D): Matrix2D = matrix.map(_.map(identity))

  def countElement(matrix: Matrix2D, element: Char): Int = matrix.map(_.count(_ == element)).sum

  def countMatrixScore(matrix: Matrix2D): Int = countElement(matrix, Tree) * countElement(matrix, Lumberyard)

  def hashMatrix(matrix: Matrix2D): String = matrix.map(_.mkString).mkString

  def calcMatrixPeriod(matrix: Matrix2D): (Int, Int) = {
    val visited = mutable.Map[String, Int]()

    @tailrec
    def calcMatrixPeriodInner(m: Matrix2D, generationId: Int): (Int, Int) = {
      val hash = hashMatrix(m)

      if (visited.contains(hash)) (generationId - visited(hash), visited(hash))
      else {
        visited.put(hash, generationId)
        calcMatrixPeriodInner(generate(m), generationId + 1)
      }
    }

    calcMatrixPeriodInner(matrix, 0)
  }

  @tailrec
  def generate(matrix: Matrix2D, numGenerations: Int = 1): Matrix2D = if (numGenerations == 0) matrix else {
    val newMatrix = copyMatrix2D(matrix)
    for (
      i <- matrix.indices;
      j <- matrix(i).indices
    ) {
      val neighborCounts = getNeighborCounts(matrix, i, j)
      newMatrix(i)(j) = matrix(i)(j) match
        case OpenGround if neighborCounts(Tree) >= 3 => Tree
        case Tree if neighborCounts(Lumberyard) >= 3 => Lumberyard
        case Lumberyard if neighborCounts(Tree) == 0 || neighborCounts(Lumberyard) == 0 => OpenGround
        case _ => matrix(i)(j)
    }
    generate(newMatrix, numGenerations - 1)
  }

  def isInBounds(matrix: Matrix2D, i: Int, j: Int): Boolean =
    i >= 0 && i < matrix.length && j >= 0 && j < matrix(i).length

  def getNeighborCounts(matrix: Matrix2D, i: Int, j: Int): Map[Char, Int] = Seq(
    (i - 1, j - 1),
    (i - 1, j),
    (i - 1, j + 1),
    (i, j - 1),
    (i, j + 1),
    (i + 1, j - 1),
    (i + 1, j),
    (i + 1, j + 1),
  )
    .filter {
      case (neighborI, neighborJ) => isInBounds(matrix, neighborI, neighborJ)
    }
    .map {
      case (neighborI, neighborJ) => matrix(neighborI)(neighborJ)
    }
    .groupBy(identity)
    .map((element, seq) => (element, seq.length))
    .withDefaultValue(0)
}
