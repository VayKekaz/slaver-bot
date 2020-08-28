package com.vk.oed.slaver.model

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.entities.Role
import java.lang.NullPointerException
import javax.persistence.Embeddable
import javax.persistence.Transient

@Embeddable
class RpgRole
constructor(
    var roleId: String?,
    var price: Double?,
    @Transient var description: String?,
    @Transient var emoji: String?,
) {

  fun asShopItem(): String =
      "${asMention()} **price: $price$** — $description"

  fun asMention(): String =
      "<@&$roleId>"

  fun discordRole(): Role? = try {
    Bot.jda.getRoleById(roleId!!)
  } catch (exception: NullPointerException) {
    null
  }

  override fun toString(): String {
    return "Role {\n" +
        "  id=$roleId,\n" +
        "  price=$price,\n" +
        "  description=$description\n" +
        "}"
  }
}
