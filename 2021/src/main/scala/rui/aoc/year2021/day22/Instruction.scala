package rui.aoc.year2021.day22

case class Instruction(op: String, cube: Cube)

object Instruction {
  private final val INSTRUCTION_REGEX =
    "(?<op>on|off) x=(?<x0>-?\\d+)..(?<x1>-?\\d+),y=(?<y0>-?\\d+)..(?<y1>-?\\d+),z=(?<z0>-?\\d+)..(?<z1>-?\\d+)".r

  def fromStr(rawInstructionStr: String): Instruction = {
    val matches = INSTRUCTION_REGEX.findFirstMatchIn(rawInstructionStr).get
    Instruction(
      matches.group("op"),
      Cube(
        matches.group("x0").toLong,
        matches.group("x1").toLong,
        matches.group("y0").toLong,
        matches.group("y1").toLong,
        matches.group("z0").toLong,
        matches.group("z1").toLong
      )
    )
  }
}
