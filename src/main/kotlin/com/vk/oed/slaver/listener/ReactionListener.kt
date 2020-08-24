package com.vk.oed.slaver.listener

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ReactionListener : ListenerAdapter() {

  override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
    TODO("when reaction goes on shop message then do logic")
  }
}