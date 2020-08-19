package com.vk.oed.slaver.command

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

interface Command {

  val sender: User
  val message: Message
  val channel: MessageChannel

  fun execute()
}