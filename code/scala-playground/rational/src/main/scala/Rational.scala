class Rational(n: Int, d: Int) extends Ord {
  require(d != 0)

  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  def this(n: Int) = this(n, 1)

  def +(that: Rational) = new Rational(numer * that.denom + that.numer * denom, denom * that.denom)

  def +(i: Int) = new Rational(numer + i * denom, denom)

  def -(i: Int) = new Rational(numer - i * denom, denom)

  def -(that: Rational) = new Rational(numer * that.denom - that.numer * denom, denom * that.denom)

  def *(that: Rational) = new Rational(numer * that.numer, denom * that.denom)

  def *(i: Int) = new Rational(numer * i, denom)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  override def toString = numer + "/" + denom

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Rational] && {
      val r = that.asInstanceOf[Rational]
      numer == r.numer && denom == r.denom
    }

  def <(that: Any): Boolean = {
    require(that.isInstanceOf[Rational], "cannot compare " + that + " and a Rational")

    val r = that.asInstanceOf[Rational]

    numer/denom < r.numer/r.denom
  }
}
