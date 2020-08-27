package com.vk.oed.slaver.action

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed

fun playerInfoEmbedTemplate(
    name: String,
    avatarUrl: String?,
    money: Double,
    slaves: Long
): MessageEmbed {
  val response = EmbedBuilder()
  response.apply {
    setAuthor(name, null, avatarUrl)
    setTitle("Info about slaver.")
    addField("Money", "${money.toInt()}$", true)
    addField("Slaves", slaves.toString(), true)
  }
  return response.build()
}