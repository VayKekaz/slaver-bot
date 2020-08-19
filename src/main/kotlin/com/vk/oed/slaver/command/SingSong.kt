package com.vk.oed.slaver.command

import com.vk.oed.slaver.enqueue
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

class SingSong(
  override val sender: User,
  override val message: Message,
  override val channel: MessageChannel
) : Command {

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