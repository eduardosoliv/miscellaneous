package squareroot

object SquareRoot {
  def sqrt(value: Double): Double = {
    def sqrtIt(guess: Double, origin: Double): Double = {
      def abs(x: Double) = if (x < 0) -x else x
      def improveGuess(guess: Double, origin: Double) = (guess + origin / guess) / 2
      def isGoodEnough(guess: Double, origin: Double) = abs(guess * guess - origin) / origin < 0.001

      if (isGoodEnough(guess, origin)) guess
      else sqrtIt(improveGuess(guess, origin), origin)
    }

    sqrtIt(1.0, value)
  }
}