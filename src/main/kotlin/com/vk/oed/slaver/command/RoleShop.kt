package com.vk.oed.slaver.command

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class RoleShop : Command {

  @Autowired private val context: ApplicationContext? = null

  override val trigger: Regex =
      Regex("shop|role shop|roles")

  override fun execute(commandData: CommandData) {
    commandData.channel.sendMessage(buildShop()).queue {
      attachButtonsTo(it)
    }
  }

  fun buildShop(): MessageEmbed {
    val response = EmbedBuilder()
    response.apply {
      val avatarUrl =
          "https://cdn.discordapp.com/avatars/745015437116571659/99f31d98f24a1d8c0ad766d2b2e90a61.png"
      setAuthor("Role Shop", null, avatarUrl)
      setDescription(buildRolesPreview())
    }
    return response.build()
  }

  fun buildRolesPreview(): String {
    return Bot.roles.joinToString("\n\n", transform = { it.asShopItem() })
  }

  private fun attachButtonsTo(message: Message) {
    val jda = context!!.getBean(JDA::class.java)
    Bot.roles.forEach {
      val emoji = jda.getEmoteById(it.emoji!!)!!
      message.addReaction(emoji).queue()
    }
  }
}