package com.vk.oed.slaver.command

import com.vk.oed.slaver.enqueue
import org.springframework.stereotype.Component

@Component
class SingSong : Command {

  override val trigger: Regex =
      Regex("sing song|song|sing a song")

  override fun execute(commandData: CommandData) {
    commandData.channel.enqueue(
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