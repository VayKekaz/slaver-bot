package com.vk.oed.slaver.model

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.util.dollarEmote
import net.dv8tion.jda.api.entities.Role
import java.lang.NullPointerException
import javax.persistence.Embeddable

@Embeddable // TODO: change to many-to-one, change to not-null types
class RpgRole
constructor(
    var roleId: String?,
    var price: Double?,
    var description: String?,
    var emoji: String?,
) {

  fun asShopItem(): String =
      "${emojiAsDiscordEmote()} ${asMention()} **price: $price$ $dollarEmote** — $description"

  fun asMention(): String =
      "<@&$roleId>"

  fun emojiAsDiscordEmote(): String =
      "<:emoji:${emoji}>"

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
