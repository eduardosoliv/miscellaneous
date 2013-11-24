import org.scalatest.FunSuite

class RationalTest extends FunSuite {
  test("Add rationals") {
    val r = new Rational(6, 12) + new Rational(24, 12)
    assert(r.numer == 5)
    assert(r.denom == 2)
  }

  test("Add integer to rational") {
    val r = new Rational(6, 12) + 2
    assert(r.numer == 5)
    assert(r.denom == 2)
  }

  test("Subtract rationals") {
    val r = new Rational(6, 12) - new Rational(24, 12)
    assert(r.numer == -3)
    assert(r.denom == 2)
  }

  test("Subtract integer from rational") {
    val r = new Rational(18, 3) - 2
    assert(r.numer == 4)
    assert(r.denom == 1)
  }

  test("ToString of ration") {
    val r = new Rational(18, 3)
    assert("6/1" == r.toString)
  }
}
