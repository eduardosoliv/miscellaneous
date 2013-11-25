package com.github.emrecipient

/**
 * This object will hold all the custom data of each recipient in key value fashion.
 */
class Custom(kv: Map[String, String]) {
  val keysvals = kv

  def isEmpty: Boolean = keysvals.isEmpty

  def get(key: String): Option[String] = keysvals.get(key)

  /**
   * Will throw `NoSuchElementException` if not found
   *
   * @param key The key too look
   * @return
   */
  def apply(key: String): String = keysvals(key)

  /**
   * @param keys A set of keys
   * @return Will return a Map of keys (String) values (Option)
   */
  def multiGet(keys: Set[String]): Map[String, Option[String]] = {
    def multiGetAux(keys: Set[String], kv: Map[String, Option[String]]): Map[String, Option[String]] =
      if (keys.isEmpty) kv
      else multiGetAux(keys.tail, kv + (keys.head -> get(keys.head)))

    multiGetAux(keys, Map.empty)
  }

  def size: Int = keysvals.size

  override def toString: String = "Custom" + "(" + keysvals.mkString(", ") + ")"
}