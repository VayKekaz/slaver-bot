package com.vk.oed.slaver.listener

import com.vk.oed.slaver.BOT_ID
import com.vk.oed.slaver.commandFrom
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class MessageListener : ListenerAdapter() {

  override fun onMessageReceived(event: MessageReceivedEvent) {
    GlobalScope.launch {
      val sender = event.author
      val message = event.message
      val channel = event.channel

      if (!isProbableCommand(sender, message, channel)) return@launch

      executeCommand(sender, message, channel)
    }
  }

  private fun isProbableCommand(sender: User, message: Message, channel: MessageChannel): Boolean =
    !sender.isBot && (message.mentionsBot() || channel.type == ChannelType.PRIVATE)

  private fun Message.mentionsBot(): Boolean =
    this.mentionedUsers.stream().anyMatch { it.id == BOT_ID }

  private fun executeCommand(sender: User, message: Message, channel: MessageChannel) =
    commandFrom(sender, message, channel)?.execute()
}