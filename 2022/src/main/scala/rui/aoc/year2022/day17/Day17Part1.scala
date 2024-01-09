package rui.aoc.year2022.day17

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.day17.Day17._
import rui.aoc.year2022.utils.FileIO

class Day17Part1 extends ProblemSolution {
  private final val NUM_PIECES = 2022

  override def solve(): AnyVal = dropPieces(
    FileIO.readResourceLine("day17.txt"),
    NUM_PIECES,
    INIT_COORDS
  )

  def dropPieces(
    airStream: String,
    numPiecesRemaining: Int,
    occupiedCoords: Set[Coord],
    maxY: Int = 0,
    pieceId: Int = 0,
    airStreamIndex: Int = 0
  ): Int = if (numPiecesRemaining == 0) maxY else {
    val piece = buildPiece(maxY + 4, pieceId)
    val (newPiece, newAirStreamIndex) = dropPiece(occupiedCoords, piece, airStream, airStreamIndex)
    dropPieces(
      airStream,
      numPiecesRemaining - 1,
      occupiedCoords.union(newPiece.coords),
      math.max(maxY, newPiece.maxY),
      (pieceId + 1) % NUM_PIECE_TYPES,
      newAirStreamIndex
    )
  }
}