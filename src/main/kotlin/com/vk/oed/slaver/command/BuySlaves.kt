package com.vk.oed.slaver.command

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.exception.LowBalanceException
import com.vk.oed.slaver.service.PlayerService
import org.springframework.stereotype.Component

@Component
class BuySlaves(
    service: PlayerService
) : RpgCommand(service) {

  override val trigger: Regex =
      Regex("buy [0-9]+ slaves?")

  override fun execute(commandData: CommandData) {
    val player = getUpdated(commandData.sender)
    val amount = parseAmount(commandData)
    val moneyCost = amount * Bot.slavePrice!!
    if (player.money < moneyCost)
      throw LowBalanceException()
    player.money -= moneyCost
    player.slaves += amount
    playerService.save(player)
  }

  fun parseAmount(commandData: CommandData): Long =
      commandData.commandClean.split(" ")[1].toLong()
}