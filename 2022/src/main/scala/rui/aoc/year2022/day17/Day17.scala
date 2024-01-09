package rui.aoc.year2022.day17

import scala.collection.mutable

object Day17 {
  private final val LEFT_BOUND = 0
  private final val RIGHT_BOUND = 6
  final val INIT_COORDS = (0 to 6).map(x => (x, 0)).toSet
  final val NUM_PIECE_TYPES = 5

  def buildPiece(y: Int, pieceId: Int): Piece = pieceId match {
    case 0 => buildPiece0(y)
    case 1 => buildPiece1(y)
    case 2 => buildPiece2(y)
    case 3 => buildPiece3(y)
    case 4 => buildPiece4(y)
  }

  def buildPiece0(y: Int): Piece = Piece(
    Set((2, y), (3, y), (4, y), (5, y)),
    2,
    5,
    y
  )

  def buildPiece1(y: Int): Piece = Piece(
    Set((3, y), (2, y + 1), (3, y + 1), (4, y + 1), (3, y + 2)),
    2,
    4,
    y + 2
  )

  def buildPiece2(y: Int): Piece = Piece(
    Set((2, y), (3, y), (4, y), (4, y + 1), (4, y + 2)),
    2,
    4,
    y + 2
  )

  def buildPiece3(y: Int): Piece = Piece(
    Set((2, y), (2, y + 1), (2, y + 2), (2, y + 3)),
    2,
    2,
    y + 3
  )

  def buildPiece4(y: Int): Piece = Piece(
    Set((2, y), (3, y), (2, y + 1), (3, y + 1)),
    2,
    3,
    y + 1
  )

  def movePieceHorizontally(piece: Piece, dir: Int): Piece =
    if (piece.maxX == RIGHT_BOUND && dir == 1) piece
    else if (piece.minX == LEFT_BOUND && dir == -1) piece
    else Piece(
      piece.coords.map {
        case (x, y) => (x + dir, y)
      },
      piece.minX + dir,
      piece.maxX + dir,
      piece.maxY
    )

  def movePieceDown(piece: Piece): Piece = Piece(
    piece.coords.map {
      case (x, y) => (x, y - 1)
    },
    piece.minX,
    piece.maxX,
    piece.maxY - 1
  )

  def dropPiece(occupiedCoords: Set[Coord], piece: Piece, airStream: String, airStreamIndex: Int): (Piece, Int) = {
    val pieceAfterHorizontalMove = airStream(airStreamIndex) match {
      case '>' => movePieceHorizontally(piece, 1)
      case '<' => movePieceHorizontally(piece, -1)
    }

    val newAirStreamIndex = (airStreamIndex + 1) % airStream.length
    val pieceToMoveVertically =
      if (pieceHasCollision(pieceAfterHorizontalMove, occupiedCoords)) piece
      else pieceAfterHorizontalMove
    val pieceAfterVerticalMove = movePieceDown(pieceToMoveVertically)

    if (pieceHasCollision(pieceAfterVerticalMove, occupiedCoords)) (pieceToMoveVertically, newAirStreamIndex)
    else dropPiece(occupiedCoords, pieceAfterVerticalMove, airStream, newAirStreamIndex)
  }

  private def pieceHasCollision(piece: Piece, occupiedCoords: Set[Coord]): Boolean =
    piece.coords.exists(coord => occupiedCoords.contains(coord))

  type Coord = (Int, Int)
}
