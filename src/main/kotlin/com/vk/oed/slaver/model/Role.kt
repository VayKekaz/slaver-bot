package com.vk.oed.slaver.model

import javax.persistence.Embeddable
import javax.persistence.Transient

@Embeddable
class Role(
    var roleId: String?,
    var price: Double?,
    @Transient var description: String?,
    @Transient var emoji: String?,
) {

  fun asShopItem(): String =
      "${asMention()} **price: $price$** — $description"

  private fun asMention(): String = "<@&$roleId>"

  override fun toString(): String {
    return "Role {\n" +
        "  id=$roleId,\n" +
        "  price=$price,\n" +
        "  description=$description\n" +
        "}"
  }
}
