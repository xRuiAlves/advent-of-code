package util

object NumUtils {
  def divisors(num: Int): IndexedSeq[Int] = (1 to Math.sqrt(num).toInt).flatMap(i =>
    if (num % i == 0) List(i, num / i)
    else List.empty
  )
}
