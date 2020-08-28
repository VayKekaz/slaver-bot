package com.vk.oed.slaver.action

interface Command {

  val trigger: Regex

  fun triggeredBy(commandData: CommandData): Boolean =
      trigger.matches(commandData.commandClean)

  fun execute(commandData: CommandData)
}