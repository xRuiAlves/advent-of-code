package rui.aoc.year2021.day23

import scala.collection.mutable

object Day23 {
  type Board = Array[String]

  private[this] final val AmphipodsCosts = Map(
    'A' -> 1,
    'B' -> 10,
    'C' -> 100,
    'D' -> 1000
  )
  private[this] final val AmphipodsRoomIndexes = Map(
    'A' -> 2,
    'B' -> 4,
    'C' -> 6,
    'D' -> 8
  )
  private[this] final val AmphipodsRoomIndexesInverted = AmphipodsRoomIndexes.map(_.swap)

  final val RoomIndexes = AmphipodsRoomIndexes.values.toSet
  final val EmptyCell = '.'
  final val Part1RoomSize = 2
  final val Part2RoomSize = 4

  def parseBoard(input: Array[Array[Char]]): Board = input.indices.map(idx =>
    if (Day23.RoomIndexes.contains(idx)) Day23.parseRoom(input, idx)
    else Day23.EmptyCell.toString
  ).toArray

  def parseInput(input: Array[String]): Array[Array[Char]] =
    input.map(_.toCharArray).transpose.drop(1).dropRight(1)

  def parseRoom(map: Array[Array[Char]], roomIdx: Int): String =
    map(roomIdx).filter(AmphipodsRoomIndexes.keySet.contains(_)).mkString

  def targetHash(mapSize: Int, roomSize: Int): String = boardHash((0 until mapSize).map(idx => {
    if (RoomIndexes.contains(idx)) AmphipodsRoomIndexesInverted(idx).toString * roomSize
    else EmptyCell.toString
  }).toArray)

  def boardHash(board: Board): String = board.mkString("-")

  def solve(board: Board): mutable.Map[String, Int] = {
    val visited = mutable.Map[String, Int]()
    val toVisit = mutable.Queue[Board]()
    visited(boardHash(board)) = 0
    toVisit.enqueue(board)

    while (toVisit.nonEmpty) {
      val curr = toVisit.dequeue()

      curr.zipWithIndex.foreach { case (amphipod, idx) =>
        if (getPieceFromRoom(amphipod).nonEmpty) {
          getPossibleMoves(curr, idx).foreach(destination => {
            val moveResult = move(curr, idx, destination)
            val newCost = visited(boardHash(curr)) + moveResult.cost
            val newBoardHash = boardHash(moveResult.board)
            val cost = visited.getOrElse(newBoardHash, Int.MaxValue)

            if (newCost < cost) {
              toVisit.enqueue(moveResult.board)
              visited(newBoardHash) = newCost
            }
          })
        }
      }
    }

    visited
  }

  def getPossibleMoves(board: Board, idx: Int): Array[Int] = {
    val amphipod = board(idx)
    val movingAmphipod = getPieceFromRoom(amphipod).get

    if (!RoomIndexes.contains(idx)) {
      val destination = AmphipodsRoomIndexes(amphipod.head)
      return (
        if (canReach(board, idx, destination) && roomOnlyContainsGoal(board, amphipod.head, destination)) Array(destination)
        else Array.empty
      )
    }

    if (idx != AmphipodsRoomIndexes(movingAmphipod) || !roomOnlyContainsGoal(board, movingAmphipod, idx)) board.indices
      .filterNot(_ == idx)
      .filterNot(destination => RoomIndexes.contains(destination) && AmphipodsRoomIndexes(movingAmphipod) != destination)
      .filterNot(destination => AmphipodsRoomIndexes(movingAmphipod) == destination && !roomOnlyContainsGoal(board, movingAmphipod, destination))
      .filter(destination => canReach(board, idx, destination))
      .toArray
    else Array.empty
  }

  def canReach(board: Board, from: Int, destination: Int): Boolean = !(math.min(from, destination) to math.max(from, destination))
    .exists(idx => idx != from && !RoomIndexes.contains(idx) && board(idx) != EmptyCell.toString)

  def roomOnlyContainsGoal(board: Board, amphipod: Char, idx: Int): Boolean = {
    board(idx).forall(cell => cell == EmptyCell || cell == amphipod)
  }

  def getPieceFromRoom(room: String): Option[Char] = room.find(_ != EmptyCell)

  case class RoomUpdateResult(roomAfterUpdate: String, numCellsTravelled: Int)

  def updateRoom(amphipod: Char, room: String): RoomUpdateResult = {
    val roomCells = room.toCharArray
    val numCellsToTravel = roomCells.count(_ == EmptyCell)
    roomCells(numCellsToTravel - 1) = amphipod
    RoomUpdateResult(roomCells.mkString, numCellsToTravel)
  }

  case class MoveResult(board: Board, cost: Int)

  def move(board: Board, idx: Int, destination: Int): MoveResult = {
    val newBoard = board.clone()
    val movingAmphipod = getPieceFromRoom(board(idx)).get
    var dist = 0

    if (board(idx).length == 1) {
      newBoard(idx) = EmptyCell.toString
    } else {
      val newRoom = new mutable.StringBuilder
      var found = false

      board(idx).foreach(cell => {
        if (cell == EmptyCell) {
          newRoom.addOne(cell)
          dist += 1
        } else if (!found) {
          newRoom.addOne(EmptyCell)
          dist += 1
          found = true
        } else {
          newRoom.addOne(cell)
        }
      })

      newBoard(idx) = newRoom.mkString
    }

    dist += math.abs(idx - destination)

    if (board(destination).length == 1) {
      newBoard(destination) = movingAmphipod.toString
    } else {
      val roomUpdateResult = updateRoom(movingAmphipod, board(destination))
      newBoard(destination) = roomUpdateResult.roomAfterUpdate
      dist += roomUpdateResult.numCellsTravelled
    }

    MoveResult(newBoard, dist * AmphipodsCosts(movingAmphipod))
  }
}

