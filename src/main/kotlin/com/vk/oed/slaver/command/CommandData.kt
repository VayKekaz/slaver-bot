package com.vk.oed.slaver.command

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class CommandData(event: MessageReceivedEvent) {

  val sender: User = event.author
  val message: Message = event.message
  val channel: MessageChannel = event.channel

  val fromBot: Boolean
    get() = sender.isBot

  val mentionsBot: Boolean
    get() = message.mentionsBot

  private val Message.mentionsBot: Boolean
    get() = this.mentionedUsers.stream().anyMatch { it.id == Bot.id }

  val fromPrivateChannel: Boolean
    get() = channel.type == ChannelType.PRIVATE

  val commandClean: String
    get() = message.commandClean

  private val Message.commandClean: String
    get() = this.contentStripped
      .replace("\\s+".toRegex(), " ")
      .substringAfter("@Slaver Bot ")
}