package rui.aoc.year2022.day25

import rui.aoc.year2022.day25.SnafuNumber.SNAFU_BASE_SIZE

case class SnafuNumber(value: String) extends AnyVal {
  def toDecimal: Long = value
    .reverse
    .zipWithIndex
    .foldLeft(0L) {
      case (acc, (digit, index)) => acc + Math.pow(SNAFU_BASE_SIZE, index).toLong * (digit match {
        case '=' => -2L
        case '-' => -1L
        case _ => (digit - '0').toLong
      })
    }

  override def toString: String = value
}

object SnafuNumber {
  private final val SNAFU_BASE_SIZE = 5

  def parseLong(decimalNumber: Long): SnafuNumber = SnafuNumber(parseLongStr(decimalNumber))

  private def parseLongStr(decimalNumber: Long): String = if (decimalNumber == 0) "" else decimalNumber % SNAFU_BASE_SIZE match {
    case 0 => s"${parseLongStr(decimalNumber / SNAFU_BASE_SIZE)}0"
    case 1 => s"${parseLongStr((decimalNumber - 1) / SNAFU_BASE_SIZE)}1"
    case 2 => s"${parseLongStr((decimalNumber - 2) / SNAFU_BASE_SIZE)}2"
    case 3 => s"${parseLongStr((decimalNumber + 2) / SNAFU_BASE_SIZE)}="
    case 4 => s"${parseLongStr((decimalNumber + 1) / SNAFU_BASE_SIZE)}-"
  }
}
