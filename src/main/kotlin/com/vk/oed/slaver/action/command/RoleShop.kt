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
    val channel = commandData.channel
    channel.sendMessage(createEmbed()).queue {
      attachBuyButtonsTo(it)
    }
  }

  fun createEmbed(): MessageEmbed {
    val response = EmbedBuilder()
    response.apply {
      setAuthor("Role Shop", null, Bot.avatarUrl)
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