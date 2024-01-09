package rui.aoc.year2021.day24

import scala.collection.mutable

object Day24 {
  sealed trait Instruction
  sealed trait InstructionPart

  case class Variable(name: String) extends InstructionPart
  case class Inp(lhs: Variable) extends Instruction

  case class Literal(value: Long) extends InstructionPart
  case class Add(lhs: Variable, rhs: InstructionPart) extends Instruction
  case class Mul(lhs: Variable, rhs: InstructionPart) extends Instruction
  case class Div(lhs: Variable, rhs: InstructionPart) extends Instruction
  case class Eql(lhs: Variable, rhs: InstructionPart) extends Instruction
  case class Mod(lhs: Variable, rhs: InstructionPart) extends Instruction

  type VisitNode = (Int, Long, Long, Long, Long)

  def parseInstructionRhs(rhs: String): InstructionPart = rhs.toLongOption match {
    case Some(value) => Literal(value)
    case None        => Variable(rhs)
  }

  def parseInstructions(input: Array[String]): Array[Instruction] = input.map {
    case s"inp $lhs"      => Inp(Variable(lhs))
    case s"add $lhs $rhs" => Add(Variable(lhs), parseInstructionRhs(rhs))
    case s"mul $lhs $rhs" => Mul(Variable(lhs), parseInstructionRhs(rhs))
    case s"div $lhs $rhs" => Div(Variable(lhs), parseInstructionRhs(rhs))
    case s"eql $lhs $rhs" => Eql(Variable(lhs), parseInstructionRhs(rhs))
    case s"mod $lhs $rhs" => Mod(Variable(lhs), parseInstructionRhs(rhs))
  }

  def evalInstructions(instructions: Array[Instruction], possibleInputValues: Array[Int]): Option[String] = {
    val visited = mutable.Set[VisitNode]()

    def evalInstruction(variables: Map[String, Long], currModelNumber: String = "", idx: Int = 0): Option[String] = {
      if (idx == instructions.length) {
        if (variables("z") == 0) Some(currModelNumber)
        else None
      } else {
        val newVariables = instructions(idx) match {
          case Add(Variable(lhs), Literal(rhs))  => variables.updated(lhs, variables(lhs) + rhs)
          case Add(Variable(lhs), Variable(rhs)) => variables.updated(lhs, variables(lhs) + variables(rhs))
          case Mul(Variable(lhs), Literal(rhs))  => variables.updated(lhs, variables(lhs) * rhs)
          case Mul(Variable(lhs), Variable(rhs)) => variables.updated(lhs, variables(lhs) * variables(rhs))
          case Div(Variable(lhs), Literal(rhs))  => variables.updated(lhs, variables(lhs) / rhs)
          case Div(Variable(lhs), Variable(rhs)) => variables.updated(lhs, variables(lhs) / variables(rhs))
          case Eql(Variable(lhs), Literal(rhs))  => variables.updated(lhs, if (variables(lhs) == rhs) 1L else 0L)
          case Eql(Variable(lhs), Variable(rhs)) => variables.updated(lhs, if (variables(lhs) == variables(rhs)) 1L else 0L)
          case Mod(Variable(lhs), Literal(rhs))  => variables.updated(lhs, variables(lhs) % rhs)
          case Mod(Variable(lhs), Variable(rhs)) => variables.updated(lhs, variables(lhs) % variables(rhs))
          case _                                 => variables
        }

        if (instructions(idx).isInstanceOf[Inp]) {
          val w = variables("w")
          val x = variables("x")
          val y = variables("y")
          val z = variables("z")
          val visitNode = (idx, w, x, y, z)

          if (!visited.contains(visitNode)) {
            visited.addOne(visitNode)
            for (input <- possibleInputValues) {
              val res = evalInstruction(variables.updated("w", input), s"$currModelNumber$input", idx + 1)
              if (res.nonEmpty) {
                return res
              }
            }
          }

          None
        } else {
          evalInstruction(newVariables, currModelNumber, idx + 1)
        }
      }
    }

    evalInstruction(Array("w", "x", "y", "z").map(_ -> 0L).toMap)
  }
}

