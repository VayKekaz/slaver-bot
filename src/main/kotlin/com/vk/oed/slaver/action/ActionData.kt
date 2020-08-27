package com.vk.oed.slaver.action

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

interface ActionData {

  val actor: User
  val message: Message
  val channel: MessageChannel

  val isActorBot: Boolean
    get() = actor.isBot
}