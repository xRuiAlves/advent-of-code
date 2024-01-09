package rui.aoc.year2022.day13

import scala.collection.mutable.ArrayBuffer

case class PacketNode(value: Option[Int], children: ArrayBuffer[PacketNode] = ArrayBuffer.empty[PacketNode]) {
  def compareTo(other: PacketNode): Int = {
    if (this.value.isDefined && other.value.isDefined) {
      if (this.value.get < other.value.get) return -1
      if (this.value.get == other.value.get) return 0
      else return 1
    }

    if (this.value.isDefined) {
      return PacketNode.singleArrayPacket(this.value.get).compareTo(other)
    }

    if (other.value.isDefined) {
      return this.compareTo(PacketNode.singleArrayPacket(other.value.get))
    }

    for (i <- 0 until math.min(this.children.size, other.children.size)) {
      val childComparisonRes = this.children(i).compareTo(other.children(i))
      if (childComparisonRes != 0) {
        return childComparisonRes
      }
    }

    if (this.children.size < other.children.size) -1
    else if (this.children.size == other.children.size) 0
    else 1
  }
}

object PacketNode {
  def empty: PacketNode = PacketNode(Option.empty[Int])

  def singleArrayPacket(value: Int): PacketNode = {
    val singleArrayPacketNode = PacketNode.empty
    singleArrayPacketNode.children.addOne(PacketNode(Option(value)))
    singleArrayPacketNode
  }

  def fromStr(nodeStr: String): (PacketNode, Int) = {
    if (nodeStr.head.isDigit) {
      val digits = nodeStr.takeWhile(_.isDigit)
      return (PacketNode(Option(digits.toInt)), digits.length)
    }

    var currIndex = 1
    val packetNode = PacketNode.empty
    while (currIndex < nodeStr.length && nodeStr(currIndex) != ']') {
      if (nodeStr(currIndex) == ',') {
        currIndex += 1
      } else {
        val parseResult = fromStr(nodeStr.substring(currIndex))
        packetNode.children.addOne(parseResult._1)
        currIndex += parseResult._2
      }
    }

    (packetNode, currIndex + 1)
  }
}
