package rui.aoc.year2022.day17

import rui.aoc.year2022.day17.Day17.Coord

case class Piece(
  coords: Set[Coord],
  minX: Int,
  maxX: Int,
  maxY: Int
)