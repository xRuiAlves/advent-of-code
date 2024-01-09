package rui.aoc.year2022.day7

import rui.aoc.year2022.day7.fs.{Dir, File}

import scala.collection.mutable

object Day7 {
  def parseDirNodes(input: Array[String]): mutable.Map[String, Dir] = {
    val dirNodes = mutable.Map[String, Dir]()
    dirNodes("/") = Dir("/")

    input.foldLeft(dirNodes("/"))((currNode, line) => line match {
      case "$ ls" => currNode
      case "$ cd .." => dirNodes(currNode.parent)
      case "$ cd /" => dirNodes("/")
      case _ if line.startsWith("$ cd") => dirNodes(currNode.child(line.substring(5)))
      case _ if line.startsWith("dir") =>
        val childDirName = currNode.child(line.substring(4))
        val childDir = Dir(childDirName)
        dirNodes(childDirName) = childDir
        currNode.children.addOne(childDir)
        currNode
      case _ => line.split(" ") match {
        case Array(size, name) =>
          currNode.children.addOne(File(name, size.toLong))
          currNode
      }
    })
    dirNodes
  }
}
