package com.vk.oed.slaver.listener

import com.vk.oed.slaver.BOT_ID
import com.vk.oed.slaver.Command
import com.vk.oed.slaver.hasCommand
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

      TODO("Fix command, passes without mention, but returns false with mention and in PM")
      if (!isValidCommand(sender, message, channel)) return@launch

      executeCommand(sender, message, channel)
    }
  }

  private fun isValidCommand(sender: User, message: Message, channel: MessageChannel): Boolean =
    sender.isBot || (!message.mentionedUsers.mentionsBot() && channel.type != ChannelType.PRIVATE)

  private fun List<User>.mentionsBot(): Boolean =
    this.stream().anyMatch { it.id == BOT_ID }

  private fun executeCommand(sender: User, message: Message, channel: MessageChannel) {
    val messageString: String = message.toString()
    when {
      messageString.hasCommand(Command.SING_SONG) ->
        channel.executeSingSong()
    }
  }

  private fun MessageChannel.executeSingSong() =
    this.sendAndQueue(
      "Солнце светит, негры пашут\n" +
          "Вот такая доля наша,\n" +
          "Эй, хозяин мне б на волю\n" +
          "Немогу я быть в загоне.\n" +
          "Мне в загоне очень тесно,\n" +
          "Ведь хотел я быть в оркестре,\n" +
          "Чтобы мы пели песню вместе."
    )

  private fun MessageChannel.sendAndQueue(message: String) =
    this.sendMessage(message).queue()
}