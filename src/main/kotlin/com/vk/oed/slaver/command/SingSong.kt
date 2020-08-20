package com.vk.oed.slaver.command

import com.vk.oed.slaver.enqueue
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

class SingSong(commandData: CommandData) : Command {

  override val sender: User = commandData.sender
  override val message: Message = commandData.message
  override val channel: MessageChannel = commandData.channel

  override fun execute() {
    channel.enqueue(
      "Солнце светит, негры пашут\n" +
          "Вот такая доля наша,\n" +
          "Эй, хозяин мне б на волю\n" +
          "Немогу я быть в загоне.\n" +
          "Мне в загоне очень тесно,\n" +
          "Ведь хотел я быть в оркестре,\n" +
          "Чтобы мы пели песню вместе."
    )
  }
}