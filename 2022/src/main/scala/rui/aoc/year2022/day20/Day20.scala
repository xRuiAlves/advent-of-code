package rui.aoc.year2022.day20

object Day20 {
  def parseList(rawNumbers: Array[String], decryptionKey: Int = 1): NumsList = {
    val list = rawNumbers
      .map(_.toLong * decryptionKey)
      .map(ListNode(_))

    list.indices.foreach(i => {
      list(i).next = list((i + 1) % list.length)
      list((i + 1) % list.length).prev = list(i)
    })

    list
  }

  def rotateList(list: NumsList): Unit = {
    list.foreach(node => {
      val moveForward = node.value > 0
      val numSteps = (math.abs(node.value) % (list.length - 1)).toInt

      for (_ <- 0 until numSteps) {
        if (moveForward) {
          val swapNode = node.next
          node.next.next.prev = node
          swapNode.prev = node.prev
          node.next = swapNode.next
          swapNode.next = node
          node.prev.next = swapNode
          node.prev = swapNode
        } else {
          val swapNode = node.prev
          node.prev.prev.next = node
          swapNode.next = node.next
          node.prev = swapNode.prev
          swapNode.prev = node
          node.next.prev = swapNode
          node.next = swapNode
        }
      }
    })
  }

  def getNodeWithValue(node: ListNode, targetValue: Int): ListNode =
    if (node.value == targetValue) node
    else getNodeWithValue(node.next, targetValue)

  def getNth(node: ListNode, nth: Int): ListNode = nth match {
    case 0 => node
    case _ => getNth(node.next, nth - 1)
  }

  def groveCoordinatesSum(list: NumsList): Long = {
    val node0 = getNodeWithValue(list.head, 0)
    Seq(1000, 2000, 3000)
      .map(n => getNth(node0, n % (list.length - 1)).value)
      .sum
  }

  type NumsList = Array[ListNode]
}
