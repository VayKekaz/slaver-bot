package com.vk.oed.slaver.listener

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.CommandInvoker
import com.vk.oed.slaver.command.CommandData
import com.vk.oed.slaver.service.PlayerService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MessageListener
@Autowired constructor(
    val playerService: PlayerService,
    val invoker: CommandInvoker

) : ListenerAdapter() {

  override fun onMessageReceived(event: MessageReceivedEvent) {
    GlobalScope.launch {
      val commandData = CommandData(event)
      if (commandData.fromBot) return@launch

      println(commandData.message.contentRaw)
      playerService.addAmountOfMoneyToUser(Bot.moneyPerMessage!!, commandData.sender)

      if (isProbableCommand(commandData))
        invoker.execute(commandData)
    }
  }

  private fun isProbableCommand(CommandData: CommandData): Boolean =
      !CommandData.fromBot && (CommandData.mentionsBot || CommandData.fromPrivateChannel)
}