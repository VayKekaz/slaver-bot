package com.vk.oed.slaver

import net.dv8tion.jda.api.entities.MessageChannel

fun MessageChannel.enqueue(message: String) =
    this.sendMessage(message).queue()

