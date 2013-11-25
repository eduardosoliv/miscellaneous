package com.github.emrecipient

/**
 * Identifies an recipient using an hash, this way the email address is not needed and anonymous
 * recipients are supported.
 */
class Hash(h: String) {
  require(h != "")

  val hash = h

  override def equals(that: Any): Boolean =
    that.isInstanceOf[Hash] && {
      val h = that.asInstanceOf[Hash]
      hash == h.hash
    }

  override def toString = hash
}
