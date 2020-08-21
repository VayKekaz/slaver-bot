package com.vk.oed.slaver.command

interface Command {

  val trigger: Regex

  fun execute(commandData: CommandData)
}