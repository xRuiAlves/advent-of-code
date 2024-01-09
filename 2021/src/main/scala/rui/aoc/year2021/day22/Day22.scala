package rui.aoc.year2021.day22

object Day22 {
  def parseInstructions(lines: Array[String]): Array[Instruction] = lines.map(Instruction.fromStr)

  def applyInstruction(instructions: Array[Instruction], cubes: Set[Cube]): Set[Cube] = instructions.headOption match {
    case None => cubes
    case Some(Instruction(op, cube)) => applyInstruction(
      instructions.drop(1),
      cubes.flatMap(_.subtract(cube)) ++ (op match {
        case "on" => Set(cube)
        case "off" => Set()
      })
    )
  }

  def cubesVolume(cubes: Set[Cube]): Long = cubes.toList.map(_.volume).sum
}

