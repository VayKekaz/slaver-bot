package com.vk.oed.slaver

import com.vk.oed.slaver.listener.MessageListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.reflect.full.declaredMemberProperties

@Component
class Bot @Autowired constructor(
    messageListener: MessageListener
) {
  private val jda: JDA

  init {
    val builder = JDABuilder.createDefault(token)
    builder.addEventListeners(messageListener)

    jda = builder.build()
  }

  companion object {
    private val botProperties = createPropertiesMap("bot.properties")
    private val economyProperties = createPropertiesMap("economy.properties")

    val token: String?
    val id: String?

    val moneyPerMessage: Double?
    val slavePrice: Double?
    val slaveMoneyPerSecond: Double?

    init {
      token = botProperties["token"]
      id = botProperties["id"]

      moneyPerMessage = economyProperties["moneyPerMessage"]?.toDoubleOrNull()
      slavePrice = economyProperties["slavePrice"]?.toDoubleOrNull()
      slaveMoneyPerSecond = economyProperties["slaveMoneyPerSecond"]?.toDoubleOrNull()

      val nullMembers = ArrayList<String>(3)
      if (token == null) nullMembers.add("token")
      if (moneyPerMessage == null) nullMembers.add("moneyPerMessage")
      if (nullMembers.size != 0)
        throw IllegalArgumentException(
            "Please provide ${nullMembers.joinToString(", ")} " +
                "parameters in resources/bot.properties"
        )

      if (moneyPerMessage == null) nullMembers.add("moneyPerMessage")
      if (slavePrice == null) nullMembers.add("slavePrice")
      if (slaveMoneyPerSecond == null) nullMembers.add("slaveMoneyPerSecond")
      if (nullMembers.size != 0)
        throw IllegalArgumentException(
            "Please provide ${nullMembers.joinToString(", ")} " +
                "parameters in resources/economy.properties"
        )
    }


    private fun createPropertiesMap(fileName: String): Map<String, String> {
      val pairs = ArrayList<Pair<String, String>>()
      val propertiesString = createPropertiesString(fileName)
      propertiesString.lines().forEach {
        if (it.count { char -> char == '=' } == 1) {
          val pair = createPropertyValuePair(it)
          pairs.add(pair)
        }
      }
      return pairs.toMap()
    }

    private fun createPropertiesString(fileName: String): String {
      val classloader = Thread.currentThread().contextClassLoader
      val inputStream = classloader.getResourceAsStream(fileName)
          ?: throw IllegalArgumentException("Please provide resources/$fileName file")
      return String(inputStream.readAllBytes())
    }

    private fun createPropertyValuePair(rawString: String): Pair<String, String> {
      return rawString.substringBefore('=').trim() to
          rawString.substringAfter('=').trim()
    }
  }
}
