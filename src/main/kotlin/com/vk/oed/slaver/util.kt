package com.vk.oed.slaver

import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.MessageEmbed

fun MessageChannel.enqueue(message: CharSequence) =
    this.sendMessage(message).queue()

fun MessageChannel.enqueue(message: MessageEmbed) =
    this.sendMessage(message).queue()

fun MessageChannel.enqueue(message: Message) =
    this.sendMessage(message).queue()

fun Message.commandClean(): String =
    this.contentRaw
        .clean()
        .substringAfter("<@!${Bot.id}> ")

fun String.clean(): String =
    this.replace(Regex("\\s+"), " ")
