package com.vk.oed.slaver

import com.vk.oed.slaver.listener.MessageListener
import net.dv8tion.jda.api.JDABuilder

private const val TOKEN_COMMANDLINE_PARAMETER = "--token="
const val BOT_ID = "745015437116571659"

fun main(args: Array<String>) {
  val token = parseToken(args)

  val builder = JDABuilder.createDefault(token)
  builder.addEventListeners(MessageListener())
  builder.build()
}

private fun parseToken(args: Array<String>) =
  args.first {
    it.startsWith(TOKEN_COMMANDLINE_PARAMETER)
  }.substringAfter(TOKEN_COMMANDLINE_PARAMETER)
