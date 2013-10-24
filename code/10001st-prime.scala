// Project Euler.net
// 10001st prime
// Problem 7
// By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
// What is the 10 001st prime number?

object Prime {
  def main(args: Array[String]) {
    println(evaluate(2, 0))
  }

  def evaluate(curr: Int, primes: Int): Int = {
    if (primes == 10001)
      return curr - 1
 
    if (isPrime(curr, curr - 1)) {
      evaluate(curr + 1, primes + 1)
    } else {
      evaluate(curr + 1, primes)
    }
  }

  def isPrime(num: Int, curr: Int): Boolean = {
    if (curr == 1)
      return true

    if (num % curr == 0)
      return false

    return isPrime(num, curr - 1)
  }
}

