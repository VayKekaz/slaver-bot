package com.vk.oed.slaver.action.button

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.ButtonData
import com.vk.oed.slaver.action.RpgButton
import com.vk.oed.slaver.model.Dungeon
import com.vk.oed.slaver.service.PlayerService
import com.vk.oed.slaver.util.discordEmojiToDigit
import com.vk.oed.slaver.util.dollarEmote
import com.vk.oed.slaver.util.enqueue
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import org.springframework.stereotype.Component

@Component
class Sortie
constructor(
    playerService: PlayerService,
) : RpgButton(playerService) {

  override val trigger: String =
      "slaver sorties"

  override fun execute(buttonData: ButtonData) {
    val (actor, _, channel, _, emoji) = buttonData
    val player = playerService.getPlayerBy(actor)
    val dungeon = findAskedDungeonBy(discordEmojiToDigit(emoji.emoji))
    dungeon ?: return
    if (player.isInSortie) {
      channel.respondUserAlreadyInSortie(actor)
      return
    }
    val dungeonPrice = dungeon.price
    if (player.money < dungeonPrice) {
      channel.respondUserHasNotEnoughMoney(actor)
      return
    }
    player.money -= dungeonPrice
    player.startSortie(dungeon)
    playerService.save(player)
    channel.respondUserStartedSortie(actor, dungeon)
  }

  private fun findAskedDungeonBy(digitEmoji: Int): Dungeon? = try {
    Bot.dungeons[digitEmoji]
  } catch (exception: IndexOutOfBoundsException) {
    null
  }

  private fun MessageChannel.respondUserAlreadyInSortie(actor: User) {
    enqueue("${actor.asMention} you already in another sortie.")
  }

  // TODO("move to util")
  private fun MessageChannel.respondUserHasNotEnoughMoney(actor: User) =
      enqueue("${actor.asMention} you don't have enough money for this purchase.")

  private fun MessageChannel.respondUserStartedSortie(actor: User, dungeon: Dungeon) = enqueue(
      "${actor.asMention} you started a sortie ${dungeon.name}. " +
          "It cost ${dungeon.price}$ $dollarEmote"
  )
}