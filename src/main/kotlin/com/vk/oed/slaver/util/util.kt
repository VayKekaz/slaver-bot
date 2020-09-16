package com.vk.oed.slaver.util

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.entities.*

fun MessageChannel.enqueue(message: CharSequence) =
    this.sendMessage(message).queue()

fun MessageChannel.enqueue(message: MessageEmbed) =
    this.sendMessage(message).queue()

fun MessageChannel.enqueue(message: Message) =
    this.sendMessage(message).queue()

fun Message.mentionsBot(): Boolean =
    this.mentionedUsers.stream().anyMatch { it.id == Bot.id }

fun Message.commandClean(): String =
    this.contentRaw
        .clean()
        .substringAfter("<@!${Bot.id}> ")

fun String.clean(): String =
    this.replace(Regex("\\s+"), " ")

fun User.isMe(): Boolean =
    this.id == Bot.id

fun User.hasRole(role: Role): Boolean =
    Bot.guild.getMemberById(this.id)!!.roles.contains(role)
