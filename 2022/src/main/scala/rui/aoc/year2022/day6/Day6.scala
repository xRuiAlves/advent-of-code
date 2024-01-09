package rui.aoc.year2022.day6

object Day6 {
  def countCharsAtMarker(dataStream: String, markerSize: Int): Int = dataStream
    .sliding(markerSize)
    .zipWithIndex
    .find(markerIndexPair => markerIndexPair._1.toSet.size == markerSize)
    .map(_._2 + markerSize)
    .get
}
