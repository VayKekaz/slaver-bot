package com.vk.oed.slaver.action

import dev.minn.jda.ktx.await
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent

class ButtonData(event: MessageReactionAddEvent) : ActionData {

  override val actor: User = event.user
      ?: runBlocking { event.retrieveUser().await() }
  override val message: Message =
      runBlocking { event.retrieveMessage().await() }
  override val channel: MessageChannel = event.channel
  val buttonAuthor = message.author
}