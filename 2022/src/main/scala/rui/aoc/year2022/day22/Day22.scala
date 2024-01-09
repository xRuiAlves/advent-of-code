package rui.aoc.year2022.day22

object Day22 {
  def readInstructions(
    instructionsStr: String,
    index: Int = 0,
    instructions: Array[Instruction] = Array.empty
  ): Array[Instruction] = if (index == instructionsStr.length) instructions else {
    val subStr = instructionsStr.substring(index)
    val numStr = subStr.takeWhile(_.isDigit)
    val dirStr = subStr.takeWhile(c => !c.isDigit)

    if (dirStr.isEmpty) readInstructions(
      instructionsStr,
      index + numStr.length,
      instructions.appended(Instruction(movementValue = Option(numStr.toInt)))
    )
    else readInstructions(
      instructionsStr,
      index + dirStr.length,
      instructions.appended(Instruction(turningDir = Option(dirStr.head)))
    )
  }

  def computePassword(
    rawInput: Array[String],
    move: (MonkeyMap, Int, State) => State
  ): Int = {
    val rawMap = rawInput.dropRight(2).map(_.toCharArray)
    val mapMaxWidth = rawMap.map(_.length).max
    val map = rawMap.map(row =>
      if (row.length == mapMaxWidth) row
      else row.appendedAll(Array.fill(mapMaxWidth - row.length) {
        ' '
      })
    )
    val instructions = readInstructions(rawInput.last)

    val startingState = State(
      map.head.zipWithIndex.find(_._1 == '.').get._2,
      0,
      0
    )

    val finalState = instructions.foldLeft(startingState) {
      case (state, instruction) => instruction.turningDir match {
        case Some(turningDir) => state.turn(turningDir)
        case None => move(map, instruction.movementValue.get, state)
      }
    }
    computePasswordTotalValue(finalState)
  }

  def computePasswordTotalValue(state: State): Int =
    (state.y + 1) * 1000 + (state.x + 1) * 4 + state.dir

  type MonkeyMap = Array[Array[Char]]
}
