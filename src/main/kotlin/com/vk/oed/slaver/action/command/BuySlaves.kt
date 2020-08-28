package com.vk.oed.slaver.action.command

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.*
import com.vk.oed.slaver.commandClean
import com.vk.oed.slaver.enqueue
import com.vk.oed.slaver.exception.LowBalanceException
import com.vk.oed.slaver.model.Player
import com.vk.oed.slaver.service.PlayerService
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class BuySlaves(
    playerService: PlayerService
) : RpgCommand(playerService) {

  override val trigger: Regex =
      Regex("buy [0-9]+ slaves?")

  override fun execute(commandData: CommandData) {
    val (sender, message, channel) = commandData
    val player = playerService.getUpdatedPlayerBy(sender)
    val slaveQuantity = getQuantityFrom(message)
    val moneyCost = slaveQuantity * Bot.slavePrice
    if (player.money < moneyCost)
      throw LowBalanceException()
    player.money -= moneyCost
    player.slaves += slaveQuantity
    playerService.save(player)
    channel.enqueue(createResponse(sender, player, moneyCost, slaveQuantity))
  }

  private fun getQuantityFrom(message: Message): Long =
      message.commandClean().split(" ")[1].toLong()

  private fun createResponse(
      sender: User,
      player: Player,
      moneyCost: Double,
      slaveQuantity: Long
  ): Message {
    val response = MessageBuilder()
    val embed =  playerInfoEmbedTemplate(
        sender.name,
        sender.avatarUrl,
        player.money,
        player.slaves
    )
    response.setContent("You bought $slaveQuantity slaves. It cost $moneyCost$.")
    response.setEmbed(embed)
    return response.build()
  }
}