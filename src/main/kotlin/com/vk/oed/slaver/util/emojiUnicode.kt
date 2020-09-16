package com.vk.oed.slaver.util

const val heavyDollarSignEmote = "\uD83D\uDCB2"
const val moneybagEmote = "\uD83D\uDCB0"
const val dollarEmote = "\uD83D\uDCB5"

fun digitToDiscordEmoji(digit: Int): String {
  return dict[digit]
      ?: throw IllegalArgumentException("Digit should be in range 1..10.")
}

fun discordEmojiToDigit(discordEmojiUnicode: String): Int {
  dict.forEach {
    if (it.value == discordEmojiUnicode) {
      return it.key
    }
  }
  throw NoSuchElementException("There is no such discord emoji.")
}

private val dict: Map<Int, String> = mapOf(
    1 to "\u0031\u20E3",
    2 to "\u0032\u20E3",
    3 to "\u0033\u20E3",
    4 to "\u0034\u20E3",
    5 to "\u0035\u20E3",
    6 to "\u0036\u20E3",
    7 to "\u0037\u20E3",
    8 to "\u0038\u20E3",
    9 to "\u0039\u20E3",
)
