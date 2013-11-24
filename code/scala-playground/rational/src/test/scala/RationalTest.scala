import org.scalatest.FunSuite

class RationalTest extends FunSuite {
  test("Add rationals") {
    assert(new Rational(6, 12) + new Rational(24, 12) === new Rational(5, 2))
  }

  test("Add integer to rational") {
    assert(new Rational(6, 12) + 2 === new Rational(5, 2))
  }

  test("Subtract rationals") {
    assert(new Rational(6, 12) - new Rational(24, 12) === new Rational(-3, 2))
  }

  test("Subtract integer from rational") {
    assert(new Rational(18, 3) - 2 === new Rational(4, 1))
  }

  test("Multiply rationals") {
    assert(new Rational(5, 10) * new Rational(3) === new Rational(3, 2))
  }

  test("Multiply rational with integer") {
    assert(new Rational(5, 10) * 3 === new Rational(3, 2))
  }

  test("ToString of ration") {
    assert("6/1" === new Rational(18, 3).toString)
  }

  test("Equals") {
    assert(new Rational(10, 3) === new Rational(10, 3))
  }

  test("Greater than operator") {
    assert(new Rational(26, 3) > new Rational(18, 3))
  }

  test("Less than operator") {
    assert(new Rational(15, 3) < new Rational(18, 3))
  }

  test("Less just accepts rational") {
    intercept[IllegalArgumentException] {
      new Rational(15, 3) < 5
    }
  }
}
