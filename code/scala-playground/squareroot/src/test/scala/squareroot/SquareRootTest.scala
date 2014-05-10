package squareroot

import org.scalatest.FunSuite

class SquareRootTest extends FunSuite {
  test("square root method works correctly") {
    assert(SquareRoot.sqrt(2) == 1.4142156862745097)
  }
}
