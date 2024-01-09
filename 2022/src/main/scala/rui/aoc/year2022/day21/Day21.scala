package rui.aoc.year2022.day21

object Day21 {
  final val ROOT_MONKEY_NAME = "root"
  final val HUMAN_MONKEY_NAME = "humn"

  def monkeyYellValue(
    monkeys: Map[String, Monkey],
    monkeyName: String
  ): Long = {
    val monkey = monkeys(monkeyName)
    monkey.value match {
      case Some(value) => value
      case None => {
        val monkeyOp = monkey.monkeyOp.get
        val leftYellValue = monkeyYellValue(monkeys, monkeyOp.leftMonkeyName)
        val rightYellValue = monkeyYellValue(monkeys, monkeyOp.rightMonkeyName)
        monkeyOp.op match {
          case '+' => leftYellValue + rightYellValue
          case '-' => leftYellValue - rightYellValue
          case '*' => leftYellValue * rightYellValue
          case '/' => leftYellValue / rightYellValue
        }
      }
    }
  }
}
