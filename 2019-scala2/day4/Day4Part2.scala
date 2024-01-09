package day4

import utils.FileReader

object Day4Part2 {
    final val PASSWORD_LENGTH : Int = 6

    def main(args: Array[String]): Unit = {
        val file_lines: Array[String] = FileReader.readFile("src/day4/input.txt").toArray
        val range: Array[Int] = file_lines(0).split("-").map(_.toInt)
        var valid_passwords_count = 0

        for (password <- range(0) to range(1)) {
            if (isValid(password.toString)) valid_passwords_count += 1
        }

        println(valid_passwords_count)
    }

    def isValid(password: String): Boolean =
        password.length == 6 &&
        hasEqualAdjacentChars(password) &&
        isNeverDecreasing(password)

    def hasEqualAdjacentChars(password: String): Boolean = {
        for (i <- 0 until (password.length - 1)) {
            if (password(i) == password(i + 1)) {
                val left_collision: Boolean = i > 0 && password(i) == password(i - 1)
                val right_collision: Boolean = i < password.length - 2 && password(i + 1) == password(i + 2)

                if (!left_collision && !right_collision) {
                    return true
                }
            }
        }
        false
    }

    def isNeverDecreasing(password: String): Boolean = {
        for (i <- 0 until (password.length - 1)) {
            if (password(i) > password(i + 1)) {
                return false
            }
        }
        true
    }
}
