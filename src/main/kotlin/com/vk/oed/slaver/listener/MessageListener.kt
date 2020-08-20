package com.vk.oed.slaver.listener

import com.vk.oed.slaver.command.Command
import com.vk.oed.slaver.command.CommandData
import com.vk.oed.slaver.model.Player
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
    val playerService: PlayerService

) : ListenerAdapter() {

  override fun onMessageReceived(event: MessageReceivedEvent) {
    GlobalScope.launch {
      val commandData = CommandData(event)
      playerService.addAmountOfMoneyToUser(5, commandData.sender)

      if (isProbableCommand(commandData))
        executeCommand(commandData)
    }
  }

  private fun isProbableCommand(commandData: CommandData): Boolean =
      !commandData.fromBot
          && (commandData.mentionsBot || commandData.fromPrivateChannel)

  private fun executeCommand(commandData: CommandData) =
      Command.from(commandData)?.execute()
}