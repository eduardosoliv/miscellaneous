import org.scalatest.Suite

class RationalTest extends Suite {
  def testAddRationals() {
    val r = new Rational(6, 12) + new Rational(24, 12)
    assert(r.numer == 5)
    assert(r.denom == 2)
  }

  def testAddInt() {
    val r = new Rational(6, 12) + 2
    assert(r.numer == 5)
    assert(r.denom == 2)
  }

  def testSubRationals() {
    val r = new Rational(6, 12) - new Rational(24, 12)
    assert(r.numer == -3)
    assert(r.denom == 2)
  }

  def testSubInt() {
    val r = new Rational(18, 3) - 2
    assert(r.numer == 4)
    assert(r.denom == 1)
  }

  def testToString() {
    val r = new Rational(18, 3)
    assert("6/1" == r.toString)
  }
}
