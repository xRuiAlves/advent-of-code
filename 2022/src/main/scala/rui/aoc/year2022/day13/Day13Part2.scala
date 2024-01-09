package rui.aoc.year2022.day13

import rui.aoc.year2022.ProblemSolution
import rui.aoc.year2022.utils.FileIO

class Day13Part2 extends ProblemSolution {
  override def solve(): AnyVal = {
    val packets = FileIO
      .readResourceLines("day13.txt")
      .filterNot(_.isBlank)
      .map(PacketNode.fromStr)
      .map(_._1)

    val dividerPacket1Index = insertionIndex("[[2]]", packets)
    val dividerPacket2Index = insertionIndex("[[6]]", packets) + 1
    dividerPacket1Index * dividerPacket2Index
  }

  def insertionIndex(packetStr: String, packets: Array[PacketNode]): Int =
    packets.count(_.compareTo(PacketNode.fromStr(packetStr)._1) < 0) + 1
}
