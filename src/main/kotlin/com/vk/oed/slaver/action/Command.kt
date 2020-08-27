package com.vk.oed.slaver.action

interface Command {

  val trigger: Regex

  fun triggeredBy(cleanCommand: String): Boolean =
      trigger.matches(cleanCommand)

  fun execute(commandData: CommandData)
}