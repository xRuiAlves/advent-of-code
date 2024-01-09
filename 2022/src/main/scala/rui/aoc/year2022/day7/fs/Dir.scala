package rui.aoc.year2022.day7.fs

import scala.collection.mutable.ArrayBuffer

case class Dir(name: String) extends FileSystemNode {
  val children = new ArrayBuffer[FileSystemNode]()

  def parent: String = name.substring(0, name.dropRight(1).lastIndexOf("/") + 1)

  def child(childNodeName: String): String = s"$name$childNodeName/"

  override def getSize: Long = {
    children.map(_.getSize).sum
  }
}
