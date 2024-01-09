package rui.aoc.year2022.day17

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day17.Day17._
import rui.aoc.year2022.utils.FileIO

class Day17Part2 extends ProblemSolution {
  private final val NUM_PIECES = 1000000000000L
  type CacheState = (Int, Int, Int, Int, Int, Int, Int, Int, Int)

  override def solve(): AnyVal = {
    val airStream = FileIO.readResourceLine("day17.txt")
    val loop = findPieceDropLoop(airStream, INIT_COORDS)
    val heightIncrementBeforeLoop = loop.heightsList(loop.start)
    val numLoops = (NUM_PIECES - loop.start) / loop.size
    val heightIncrementWithinLoop = loop.heightIncrement * ((NUM_PIECES - loop.start) / loop.size)
    val numPiecesAfterLoop = (NUM_PIECES - (loop.start + numLoops * loop.size)).toInt
    val heightIncrementAfterLoop = loop.heightsList(loop.start + numPiecesAfterLoop) - loop.heightsList(loop.start)

    heightIncrementBeforeLoop + heightIncrementWithinLoop + heightIncrementAfterLoop
  }

  def findPieceDropLoop(
    airStream: String,
    occupiedCoords: Set[Coord],
    cache: Map[CacheState, Int] = Map.empty,
    pieceDropNum: Int = 0,
    maxYList: Array[Int] = Array(0),
    maxYbyXcoord: Array[Int] = Array.fill(7){0},
    pieceId: Int = 0,
    airStreamIndex: Int = 0
  ): PieceDropLoop = {
    val piece = buildPiece(maxYList.last + 4, pieceId)
    val (newPiece, newAirStreamIndex) = dropPiece(occupiedCoords, piece, airStream, airStreamIndex)
    val newPieceId = (pieceId + 1) % NUM_PIECE_TYPES
    val state = computeState(newPiece, maxYbyXcoord, newPieceId, newAirStreamIndex)
    val newMaxYList = maxYList.appended(math.max(maxYList.last, newPiece.maxY))

    cache.get(state) match {
      case Some(prevPieceDropNum) => return PieceDropLoop(prevPieceDropNum, pieceDropNum, newMaxYList)
      case None =>
    }

    findPieceDropLoop(
      airStream,
      occupiedCoords.union(newPiece.coords),
      cache + (state -> pieceDropNum),
      pieceDropNum + 1,
      newMaxYList,
      maxYbyXcoord,
      newPieceId,
      newAirStreamIndex
    )
  }

  def computeState(piece: Piece, maxYbyXcoord: Array[Int], pieceId: Int, airStreamIndex: Int): CacheState = {
    piece.coords.foreach {
      case (x, y) => maxYbyXcoord(x) = math.max(maxYbyXcoord(x), y)
    }
    val minMaxY = maxYbyXcoord.min
    maxYbyXcoord.map(_ - minMaxY) match {
      case Array(x0, x1, x2, x3, x4, x5, x6) => (x0, x1, x2, x3, x4, x5, x6, pieceId, airStreamIndex)
    }
  }

  case class PieceDropLoop(start: Int, end: Int, heightsList: Array[Int]) {
    val size: Int = end - start
    val heightIncrement: Int = heightsList(end) - heightsList(start)
  }
}