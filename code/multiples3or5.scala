// Project Euler.net
// Multiples of 3 and 5 (correction Multiples of 3 or 5)
// Problem 1
// If we list all the natural numbers below 10 that are multiples of 3 or 5, we get 3, 5, 6 and 9. The sum of these multiples is 23.
// Find the sum of all the multiples of 3 or 5 below 1000.

import scala.language.implicitConversions
import scala.language.postfixOps

object Mult3or5 {
  val start = 1000;

  class asInt(b: Boolean) {
    def toInt = if(b) 1 else 0
  }
  implicit def convertBooleanToInt(b: Boolean) = new asInt(b)

  def main(args: Array[String]) {
      println(mult3or5(start - 1))
  }

  def mult3or5(num: Int): Int = {
    if (num == 0)
      return 0

    return ((num % 5 == 0 || num % 3 == 0) toInt) * num + mult3or5(num - 1)
  }
}

