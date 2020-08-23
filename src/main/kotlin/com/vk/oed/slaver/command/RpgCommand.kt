package com.vk.oed.slaver.command

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.model.Player
import com.vk.oed.slaver.service.PlayerService
import net.dv8tion.jda.api.entities.User
import java.time.Duration
import java.time.Instant

abstract class RpgCommand(
    val playerService: PlayerService
) : Command {

  fun getUpdated(user: User): Player {
    val player = playerService.getPlayerBy(user)
    val now = Instant.now()
    val timeDifference = Duration.between(player.lastBalanceUpdate, now).abs().toSeconds()

    player.money += player.slaves * Bot.slaveMoneyPerSecond!! * timeDifference
    player.lastBalanceUpdate = now
    return player
  }
}
