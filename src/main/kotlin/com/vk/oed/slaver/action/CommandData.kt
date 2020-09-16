package com.vk.oed.slaver.action

import com.vk.oed.slaver.util.commandClean
import com.vk.oed.slaver.util.mentionsBot
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class CommandData(event: MessageReceivedEvent) : ActionData {

  override val actor: User = event.author
  override val message: Message = event.message
  override val channel: MessageChannel = event.channel

  val mentionsMe: Boolean
    get() = message.mentionsBot()

  val fromPrivateChannel: Boolean
    get() = channel.type == ChannelType.PRIVATE

  val commandClean: String
    get() = message.commandClean()

  override fun toString(): String {
    return "CommandData {\n" +
        "  sender: ${this.actor.asTag}\n" +
        "  message raw: ${this.message.contentRaw}\n" +
        "  message clean: ${this.commandClean}\n" +
        "  channel: ${this.channel.name}\n" +
        "}"
  }
}