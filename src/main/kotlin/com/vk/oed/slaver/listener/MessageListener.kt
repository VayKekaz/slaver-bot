package com.vk.oed.slaver.listener

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.CommandData
import com.vk.oed.slaver.invoker.CommandInvoker
import com.vk.oed.slaver.service.PlayerService
import dev.minn.jda.ktx.CoroutineEventListener
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.EventListener
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MessageListener
@Autowired constructor(
    private val playerService: PlayerService,
    private val invoker: CommandInvoker

) {

  suspend fun onMessageReceived(event: MessageReceivedEvent) {
    coroutineScope {
      val commandData = CommandData(event)
      if (commandData.isActorBot) return@coroutineScope

      println(commandData.message.contentRaw)
      playerService.addAmountOfMoneyToUser(Bot.moneyPerMessage, commandData.actor)

      if (isProbableCommand(commandData))
        invoker.invoke(commandData)
    }
  }

  private fun isProbableCommand(CommandData: CommandData): Boolean {
    return !CommandData.isActorBot &&
        (CommandData.mentionsMe || CommandData.fromPrivateChannel)
  }
}
