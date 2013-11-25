package com.github.emrecipient.tests

import com.github.emrecipient.Custom
import org.scalatest.FunSuite

class CustomTest extends FunSuite {
  test("Empty") {
    assert(new Custom(Map.empty).isEmpty)
  }

  test("Get") {
    assert(getCustom.get("hello").get === "world")
  }

  test("Absent get") {
    assert(getCustom.get("test").getOrElse("") === "")
  }

  test("Apply") {
    assert(getCustom("hello") === "world")
  }

  test("Apply Absent ") {
    intercept[NoSuchElementException] {
      getCustom("absent")
    }
  }

  test("Multi get") {
    val keysvals = getCustom.multiGet(Set("hello", "world"))
    assert(keysvals.size == 2)
    assert(keysvals("hello").isInstanceOf[Some[String]])
    assert(keysvals("hello").get === "world")
    assert(!keysvals("world").isDefined)
  }

  test("Size") {
    assert(getCustom.size === 2)
  }

  test("To string") {
    assert(getCustom.toString === "Custom(hello -> world, one -> more)")
  }

  def getCustom: Custom = new Custom(
    Map(
      "hello" -> "world",
      "one" -> "more"
    )
  )
}
