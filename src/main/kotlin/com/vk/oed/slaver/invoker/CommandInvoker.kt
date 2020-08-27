package com.vk.oed.slaver.invoker

import com.vk.oed.slaver.action.Command
import com.vk.oed.slaver.action.CommandData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommandInvoker
@Autowired constructor(
    private val commandActions: Array<out Command>
) {

  fun invoke(commandData: CommandData) {
    findMatchingAction(commandData)?.let {
      println("EXEC cmd: ${it::class.simpleName}" +
          "with $commandData")
      it.execute(commandData)
    }
        ?: println("WRONG cmd: $commandData")
  }

  private fun findMatchingAction(commandData: CommandData): Command? {
    val cleanCommand = commandData.commandClean
    return try {
      commandActions.first { it.triggeredBy(cleanCommand) }
    } catch (exception: NoSuchElementException) {
      null
    }
  }
}