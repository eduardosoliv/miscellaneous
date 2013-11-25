package com.github.emrecipient.tests

import com.github.emrecipient.Hash
import org.scalatest.FunSuite

class HashTest extends FunSuite {
  test("Empty hash fails") {
    intercept[IllegalArgumentException] {
      new Hash("")
    }
  }

  test("Equals") {
    assert(new Hash("test") === new Hash("test"))
  }

  test("To String") {
    val hash = "asddasda"
    assert(hash === new Hash("asddasda").toString)
  }
}
