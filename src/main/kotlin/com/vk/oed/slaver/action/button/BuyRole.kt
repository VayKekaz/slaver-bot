package com.vk.oed.slaver.action.button

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.action.ButtonData
import com.vk.oed.slaver.action.RpgButton
import com.vk.oed.slaver.util.enqueue
import com.vk.oed.slaver.util.hasRole
import com.vk.oed.slaver.model.RpgRole
import com.vk.oed.slaver.service.PlayerService
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageReaction
import net.dv8tion.jda.api.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BuyRole
@Autowired constructor(
    playerService: PlayerService,
) : RpgButton(playerService) {

  override val trigger: String =
      "slaver role shop"

  override fun execute(buttonData: ButtonData) {
    val (actor, message, channel, _, emoji) = buttonData
    val askedRole = findAskedRoleBy(emoji)
    askedRole ?: return // it was not a button
    val discordRole = askedRole.discordRole()!!
    val discordEmote = Bot.jda.getEmoteById(emoji.id)!!
    message.removeReaction(discordEmote, actor).queue()
    if (actor.hasRole(discordRole)) {
      channel.respondUserAlreadyHaveThisRole(actor)
      return
    }
    val player = playerService.getUpdatedPlayerBy(actor)
    if (player.money < askedRole.price!!) {
      channel.respondUserHasNotEnoughMoney(actor)
      return
    }
    // Buy role
    val previousRole = player.rpgRole?.discordRole()
    if (previousRole != null) {
      Bot.guild.removeRoleFromMember(actor.id, previousRole).queue()
    }
    Bot.guild.addRoleToMember(actor.id, discordRole).queue()
    player.money -= askedRole.price!!
    player.rpgRole = askedRole
    playerService.save(player)
    channel.respondUserBoughtRole(actor, askedRole)
  }

  private fun findAskedRoleBy(emote: MessageReaction.ReactionEmote): RpgRole? = try {
    Bot.rpgRoles.first { it.emoji == emote.id }
  } catch (exception: NoSuchElementException) {
    null
  }

  private fun MessageChannel.respondUserAlreadyHaveThisRole(actor: User) {
    enqueue("${actor.asMention} you already have this role.")
  }

  private fun MessageChannel.respondUserHasNotEnoughMoney(actor: User) =
      enqueue("${actor.asMention} you don't have enough money for this purchase.")

  private fun MessageChannel.respondUserBoughtRole(actor: User, rpgRole: RpgRole) = enqueue(
      "${actor.asMention} you bought role ${rpgRole.asMention()} ${rpgRole.emojiAsDiscordEmote()}>")
}