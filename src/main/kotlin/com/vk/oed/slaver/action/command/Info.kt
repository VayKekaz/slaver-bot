package com.vk.oed.slaver.action.command

import com.vk.oed.slaver.action.CommandData
import com.vk.oed.slaver.action.RpgAction
import com.vk.oed.slaver.action.RpgCommand
import com.vk.oed.slaver.action.playerInfoEmbedTemplate
import com.vk.oed.slaver.enqueue
import com.vk.oed.slaver.service.PlayerService
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

  fun createResponse(sender: User): MessageEmbed {
    val player = playerService.getUpdatedPlayerBy(sender)
    return playerInfoEmbedTemplate(
        sender.name,
        sender.avatarUrl,
        player.money,
        player.slaves
    )
  }
}
