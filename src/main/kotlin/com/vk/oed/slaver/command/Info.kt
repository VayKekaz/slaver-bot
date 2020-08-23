package com.vk.oed.slaver.command

import com.vk.oed.slaver.enqueue
import com.vk.oed.slaver.service.PlayerService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Info @Autowired constructor(
    playerService: PlayerService
) : RpgCommand(playerService) {

  override val trigger: Regex =
      Regex("info")

  override fun execute(commandData: CommandData) {
    val (sender, _, channel) = commandData
    val response = createResponse(sender)
    channel.enqueue(response)
  }

  private fun createResponse(sender: User): MessageEmbed {
    val player = getUpdated(sender)
    val response = EmbedBuilder()
    response.apply {
      setAuthor(sender.name, null, sender.avatarUrl)
      setTitle("Info about slaver.")
      addField("Money", player.money.toString(), true)
      addField("Slaves", player.slaves.toString(),true)
    }
    return response.build()
  }
}
