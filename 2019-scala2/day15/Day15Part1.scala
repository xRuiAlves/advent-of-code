package day15

import utils.FileReader

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Day15Part1 {
    final class ListNode(val pos: (Int, Int), val prev: ListNode = null)

    final val PROGRAM_BUFFER_SIZE = 2000
    final val MAP_SIZE = 50
    final val map = Array.ofDim[Byte](MAP_SIZE, MAP_SIZE)

    // directions
    final val NORTH: Byte = 1
    final val SOUTH: Byte = 2
    final val WEST: Byte = 3
    final val EAST: Byte = 4

    // map
    final val VOID: Byte = 0
    final val UNEXPLORED: Byte = 1
    final val EXPLORED: Byte = 2
    final val WALL: Byte = 3
    final val OXYGEN_SYSTEM: Byte = 4

    // responses
    final val WALL_HIT: Byte = 0
    final val MOVED: Byte = 1
    final val FOUND_OS: Byte = 2


    def main(args: Array[String]): Unit = {
        val file_lines : Array[String] = FileReader.readFile("src/day15/input.txt").toArray
        val pr = new ProgramRunner(createProgram(file_lines))

        fillMap(pr)
        val path = bfs((MAP_SIZE / 2, MAP_SIZE / 2), true)

        println(path.length)
    }

    def fillMap(pr: ProgramRunner): Unit = {
        var pos = (MAP_SIZE / 2, MAP_SIZE / 2)

        putMap(pos, EXPLORED)
        getAdjacent(pos).foreach(adj => {
            if (getMap(adj) == VOID) {
                putMap(adj, UNEXPLORED)
            }
        })

        while (true) {
            val path = bfs(pos)
            if (path == null) return

            pos = move(pos, pr, path)

            getAdjacent(pos).foreach(adj => {
                if (getMap(adj) == VOID) {
                    putMap(adj, UNEXPLORED)
                }
            })
        }
    }

    def bfs(start_pos: (Int, Int), target_os: Boolean = false): ArrayBuffer[Byte] = {
        val visited = new mutable.HashSet[(Int, Int)]()
        val queue = new mutable.Queue[ListNode]()

        visited.add(start_pos)
        queue.enqueue(new ListNode(start_pos))

        while (queue.nonEmpty) {
            val curr = queue.dequeue

            if ((!target_os && getMap(curr.pos) == UNEXPLORED) || (target_os && getMap(curr.pos) == OXYGEN_SYSTEM)) {
                return buildPath(curr)
            }

            getAdjacent(curr.pos).foreach(adj => {
                if (isTraversable(adj) && !visited.contains(adj)) {
                    visited.add(adj)
                    queue.enqueue(new ListNode(adj, curr))
                }
            })
        }

        null
    }

    def buildPath(node: ListNode): ArrayBuffer[Byte] = {
        val path = new ArrayBuffer[Byte]()
        var curr = node

        while (curr.prev != null) {
            val p_to = curr.pos
            val p_from = curr.prev.pos

            path.addOne(
                if (p_to._1 - p_from._1 > 0) EAST
                else if (p_to._1 - p_from._1 < 0) WEST
                else if (p_to._2 - p_from._2 > 0) NORTH
                else SOUTH
            )

            curr = curr.prev
        }

        path.reverse
    }

    def move(pos: (Int, Int), pr: ProgramRunner, path: ArrayBuffer[Byte]): (Int, Int) = {
        var curr_pos = pos

        for (i <- 0 until path.length - 1) {
            val dir = path(i)
            move(pr, dir)
            curr_pos = moveDir(curr_pos, dir)
        }

        val target_pos = moveDir(curr_pos, path.last)

        move(pr, path.last) match {
            case WALL_HIT => {
                putMap(target_pos, WALL)
                curr_pos
            }
            case MOVED => {
                putMap(target_pos, EXPLORED)
                target_pos
            }
            case FOUND_OS => {
                putMap(target_pos, OXYGEN_SYSTEM)
                target_pos
            }
            case res => throw new Exception(s"Unknown response: $res")
        }
    }

    def putMap(pos: (Int, Int), value: Byte): Unit = pos match {
        case (x, y) => map(y)(x) = value
    }

    def getMap(pos: (Int, Int)): Byte = map(pos._2)(pos._1)

    def move(pr: ProgramRunner, dir: Byte): Byte = pr.run(dir).toByte

    def isTraversable(pos: (Int, Int)): Boolean = getMap(pos) match {
        case UNEXPLORED => true
        case EXPLORED => true
        case OXYGEN_SYSTEM => true
        case _ => false
    }

    def getAdjacent(pos: (Int, Int)): List[(Int, Int)] = List(
        (pos._1 + 1, pos._2),
        (pos._1 - 1, pos._2),
        (pos._1, pos._2 + 1),
        (pos._1, pos._2 - 1)
    )

    def moveDir(pos: (Int, Int), dir: Byte): (Int, Int) = dir match {
        case EAST => (pos._1 + 1, pos._2)
        case WEST => (pos._1 - 1, pos._2)
        case NORTH => (pos._1, pos._2 + 1)
        case SOUTH => (pos._1, pos._2 - 1)
    }

    def createProgram(raw_input: Array[String]): Array[BigInt] = {
        val original_program : Array[BigInt] = raw_input(0).split(",").map(BigInt(_))
        val program : Array[BigInt] = Array.fill(PROGRAM_BUFFER_SIZE)(0)
        Array.copy(original_program, 0, program, 0, original_program.length)
        program
    }

    def printMap(): Unit = {
        map.foreach(line => println(line.mkString("")))
    }
}
