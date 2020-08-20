package com.vk.oed.slaver.command

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

abstract class Command(commandData: CommandData) {

  val sender: User = commandData.sender
  val message: Message = commandData.message
  val channel: MessageChannel = commandData.channel

  abstract fun execute()

  companion object {
    fun from(commandData: CommandData): Command? {
      return when (commandData.commandClean) {
        "sing song", "sing a song", "спой песню" ->
          SingSong(commandData)
        else -> null
      }
    }
  }
}