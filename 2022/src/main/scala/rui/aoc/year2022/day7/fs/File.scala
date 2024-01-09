package rui.aoc.year2022.day7.fs

case class File(name: String, size: Long) extends FileSystemNode {
  override def getSize: Long = size
}
