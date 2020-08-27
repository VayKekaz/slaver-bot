package com.vk.oed.slaver.listener

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.ReadyEvent

class OnReadyValidator {

  fun onReady(event: ReadyEvent) {
    val jda = event.jda

    validateProperties()
    jda.validateRoles()
  }

  /**
   * Function that throws exception if you didnt provide or
   * provided wrong parameters in .json files
   */
  private fun validateProperties() {
    val nullMembers = ArrayList<String>(3)
    if (Bot.token == null) nullMembers.add("token")
    if (Bot.name == null) nullMembers.add("name")
    if (nullMembers.size != 0)
      throw IllegalArgumentException(
          "Please provide ${nullMembers.joinToString(", ")} " +
              "parameters in resources/bot.properties"
      )

    if (Bot.moneyPerMessage == null) nullMembers.add("moneyPerMessage")
    if (Bot.slavePrice == null) nullMembers.add("slavePrice")
    if (Bot.slaveMoneyPerSecond == null) nullMembers.add("slaveMoneyPerSecond")
    if (nullMembers.size != 0)
      throw IllegalArgumentException(
          "Please provide ${nullMembers.joinToString(", ")} " +
              "parameters in resources/economy.properties"
      )
  }

  private fun JDA.validateRoles() {
    if (Bot.roles.any { this.getRoleById(it.roleId!!) == null }) {
      throw IllegalArgumentException("Please provide valid role IDs in resources/roles.json file.")
    }
    if (Bot.roles.any { getEmoteById(it.emoji!!) == null }) {
      throw IllegalArgumentException("Please provide valid emoji IDs in resources/roles.json file.")
    }
  }
}