package com.vk.oed.slaver.action

import com.vk.oed.slaver.isMe
import dev.minn.jda.ktx.await
import kotlinx.coroutines.coroutineScope
import net.dv8tion.jda.api.entities.*
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import java.lang.NullPointerException

class ButtonData
private constructor(
    override val actor: User,
    override val message: Message,
    override val channel: MessageChannel,
    val buttonAuthor: User,
    val emoji: MessageReaction.ReactionEmote
) : ActionData {
  companion object {
    // Constructor
    suspend operator fun invoke(event: MessageReactionAddEvent) = coroutineScope {
      val actor = event.user ?: event.retrieveUser().await()
      val message = event.retrieveMessage().await()
      val channel = event.channel
      val buttonAuthor = message.author
      return@coroutineScope ButtonData(
          actor,
          message,
          channel,
          buttonAuthor,
          event.reactionEmote
      )
    }
  }

  val reactedOnMyMessage: Boolean
    get() = buttonAuthor.isMe()

  val marker: String
    get() = try {
      message.embeds[0].footer!!.text!!
    } catch (exception: NullPointerException) {
      ""
    }

  operator fun component4(): User = buttonAuthor
  operator fun component5(): MessageReaction.ReactionEmote = emoji
}
