package com.vk.oed.slaver.action.command

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.Command
import com.vk.oed.slaver.action.CommandData
import com.vk.oed.slaver.util.digitToDiscordEmoji
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import org.springframework.stereotype.Component

@Component
class SortieShop : Command {
  override val trigger: Regex =
      Regex("dungeons?|sorties?")

  override fun execute(commandData: CommandData) {
    val channel = commandData.channel
    channel.sendMessage(createEmbed()).queue {
      attachButtonsTo(it)
    }
  }

  private fun createEmbed(): MessageEmbed {
    val builder = EmbedBuilder()
    builder.apply {
      setAuthor("Dungeons", null, Bot.avatarUrl)
      setDescription(buildDungeonsPreview())
      setFooter("slaver sorties")
    }
    return builder.build()
  }

  private fun buildDungeonsPreview(): String {
    return Bot.dungeons.joinToString(
        "\n\n",
        transform = {
          "${digitToDiscordEmoji(Bot.dungeons.indexOf(it) + 1)} ${it.asShopItem()}"
        }
    )
  }

  private fun attachButtonsTo(message: Message) {
    val length = Bot.dungeons.size
    for (i in 1..length) {
      message.addReaction(digitToDiscordEmoji(i)).queue()
    }
  }
}