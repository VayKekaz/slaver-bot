package com.vk.oed.slaver.listener

import com.vk.oed.slaver.command.Command
import com.vk.oed.slaver.command.CommandData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

  override fun onMessageReceived(event: MessageReceivedEvent) {
    GlobalScope.launch {
      val commandData = CommandData(event)

      if (!isProbableCommand(commandData))
        return@launch

      executeCommand(commandData)
    }
  }

  private fun isProbableCommand(commandData: CommandData): Boolean =
    !commandData.fromBot
        && (commandData.mentionsBot || commandData.fromPrivateChannel)

  private fun executeCommand(commandData: CommandData) =
    Command.from(commandData)?.execute()
}