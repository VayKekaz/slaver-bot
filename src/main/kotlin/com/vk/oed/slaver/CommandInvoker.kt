package com.vk.oed.slaver

import com.vk.oed.slaver.command.Command
import com.vk.oed.slaver.command.CommandData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CommandInvoker @Autowired constructor(
    val commands: Array<out Command>
) {

  fun execute(commandData: CommandData) {
    findMatchingCommand(commandData)?.let {
      println("EXEC cmd: ${it::class.simpleName}\n" +
          "with $commandData")
      it.execute(commandData)
    } ?: run {
      println("WRONG cmd: $commandData")
    }
  }

  fun findMatchingCommand(commandData: CommandData): Command? {
    val cleanCommand = commandData.commandClean
    return try {
      commands.first { it.trigger.matches(cleanCommand) }
    } catch (exception: NoSuchElementException) {
      null
    }
  }
}