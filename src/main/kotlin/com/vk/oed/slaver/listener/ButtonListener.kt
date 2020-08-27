package com.vk.oed.slaver.listener

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.ButtonData
import dev.minn.jda.ktx.await
import kotlinx.coroutines.*
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.RestAction
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class ButtonListener : ListenerAdapter() {

  override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
    runBlocking {
      val buttonData = ButtonData(event)
      if (event.reactedOnMyMessage()) {
        TODO("do things")
      }
    }
  }

  suspend fun MessageReactionAddEvent.reactedOnMyMessage(): Boolean {
    val event = this
    return coroutineScope {
      val messageRestAction = event.retrieveMessage()
      val reactionAuthor = messageRestAction.map(Message::getAuthor).submit().await()
      return@coroutineScope reactionAuthor.id == Bot.id
    }
  }
}
