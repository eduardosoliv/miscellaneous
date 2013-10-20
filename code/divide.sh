#!/bin/sh
exec scala "$0" "$@"
!#
/**
 * Divide without using / or *
 *
 * Eg: ./divide.sh 17 4
 */

object ScalaApp {
  def main(args: Array[String]) {
    if (args.length != 2) {
      println("Use ./divide.sh <numerator> <denominator>")
      System.exit(1)
    }

    if (!isAllDigits(args(0)) || !isAllDigits(args(1))) {
      println("Both numerator and denominator must be integers.")
      System.exit(1)
    }

    println(divide(args(0).toInt, args(1).toInt))
  }

  def divide(num: Int, denom: Int): Int =
  {
    if (num < denom)
      return 0

    return 1 + divide(num - denom, denom)
  }

  def isAllDigits(x: String) = x forall Character.isDigit
}

