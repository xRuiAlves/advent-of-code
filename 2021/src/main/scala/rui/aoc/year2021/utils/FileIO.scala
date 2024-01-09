package rui.aoc.year2021.utils

import scala.io.Source
import scala.util.Using

object FileIO {
  def readResourceLines(resourceName: String): Array[String] = {
    Using(Source.fromResource(resourceName)) { _.getLines().toArray }.get
  }

  def readResourceLine(resourceName: String): String = {
    readResourceLines(resourceName).head
  }
}
