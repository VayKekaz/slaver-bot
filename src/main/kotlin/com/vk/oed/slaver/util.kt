package com.vk.oed.slaver

import com.vk.oed.slaver.command.Command
import com.vk.oed.slaver.command.SingSong
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

fun MessageChannel.enqueue(message: String) =
  this.sendMessage(message).queue()

fun commandFrom(sender: User, message: Message, channel: MessageChannel): Command? {
  return when (message.clean()) {
    "sing song", "sing a song", "спой песню" ->
      SingSong(sender, message, channel)
    else -> null
  }
}

private fun Message.clean(): String =
  this.contentStripped
    .replace("\\s+".toRegex(), " ")
    .substringAfter("@Slaver Bot ")
