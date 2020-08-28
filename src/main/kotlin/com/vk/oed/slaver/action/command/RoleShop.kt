package com.vk.oed.slaver.action.command

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.Command
import com.vk.oed.slaver.action.CommandData
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import org.springframework.stereotype.Component

@Component
class RoleShop : Command {

  override val trigger: Regex =
      Regex("shop|role shop|roles")

  override fun execute(commandData: CommandData) {
    commandData.channel.sendMessage(buildShop()).queue {
      attachBuyButtonsTo(it)
    }
  }

  fun buildShop(): MessageEmbed {
    val response = EmbedBuilder()
    response.apply {
      val avatarUrl =
          "https://cdn.discordapp.com/avatars/745015437116571659/99f31d98f24a1d8c0ad766d2b2e90a61.png"
      setAuthor("Role Shop", null, avatarUrl)
      setDescription(buildRolesPreview())
      setFooter("slaver role shop")
    }
    return response.build()
  }

  fun buildRolesPreview(): String {
    return Bot.rpgRoles.joinToString("\n\n", transform = { it.asShopItem() })
  }

  private fun attachBuyButtonsTo(message: Message) {
    Bot.rpgRoles.forEach {
      val emoji = Bot.jda.getEmoteById(it.emoji!!)!!
      message.addReaction(emoji).queue()
    }
  }
}