package com.vk.oed.slaver

enum class Command(val variants: Array<String>, val message: String?) {
  SING_SONG(
    arrayOf(
      "sing a song", "sing song", "спой песню"
    ),
    "Солнце светит, негры пашут\n" +
        "Вот такая доля наша,\n" +
        "Эй, хозяин мне б на волю\n" +
        "Немогу я быть в загоне.\n" +
        "Мне в загоне очень тесно,\n" +
        "Ведь хотел я быть в оркестре,\n" +
        "Чтобы мы пели песню вместе."
  )
}

fun String.hasCommand(command: Command): Boolean =
  command.variants.any { this.contains(it) }
