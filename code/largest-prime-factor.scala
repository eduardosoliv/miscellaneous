// Project Euler.net
// Largest prime factor
// Problem 3
// The prime factors of 13195 are 5, 7, 13 and 29.
// What is the largest prime factor of the number 600851475143 ?

object Prime {
  val originNum = 600851475143.0

  def main(args: Array[String]) {
      println(evaluate(originNum, (scala.math.sqrt(originNum)).toInt - 1))
  }

  def evaluate(originNum: Double, curr: Int): Int = {
    if (curr == 1)
      return 1

    if ((originNum % curr == 0) && isPrime(curr, curr - 1))
      return curr

    evaluate(originNum, curr - 1)
  }

  def isPrime(num: Int, curr: Int): Boolean = {
    if (curr == 1)
      return true

    if (num % curr == 0)
      return false

    return isPrime(num, curr - 1)
  }
}

