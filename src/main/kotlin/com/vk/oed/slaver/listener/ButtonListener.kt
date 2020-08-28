package com.vk.oed.slaver.listener

import com.vk.oed.slaver.action.ButtonData
import com.vk.oed.slaver.invoker.ButtonInvoker
import kotlinx.coroutines.coroutineScope
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ButtonListener
@Autowired constructor(
    private val invoker: ButtonInvoker
) {

  suspend fun onMessageReactionAdd(event: MessageReactionAddEvent) {
    coroutineScope {
      val buttonData = ButtonData(event)
      if (!buttonData.reactedOnMyMessage || buttonData.isActorBot) return@coroutineScope

      invoker.invoke(buttonData)
    }
  }
}
