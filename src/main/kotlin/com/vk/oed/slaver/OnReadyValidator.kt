package com.vk.oed.slaver

import net.dv8tion.jda.api.JDA


fun validateBot() {
  Bot.jda.validateRoles()
}

private fun JDA.validateRoles() {
  if (Bot.rpgRoles.any { this.getRoleById(it.roleId!!) == null }) {
    throw IllegalArgumentException("Please provide valid role IDs in resources/roles.json file.")
  }
  if (Bot.rpgRoles.any { getEmoteById(it.emoji!!) == null }) {
    throw IllegalArgumentException("Please provide valid emoji IDs in resources/roles.json file.")
  }
}
