package com.vk.oed.slaver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.oed.slaver.listener.MessageListener
import com.vk.oed.slaver.model.Role
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class Bot @Autowired constructor(
    messageListener: MessageListener
) {

  private val jda: JDA

  init {
    val builder = JDABuilder.createDefault(token)
    builder.addEventListeners(messageListener)
    jda = builder.build()

    id = jda.selfUser.id
  }

  @Bean fun jda(): JDA = jda

  companion object Properties {
    // bot
    val token: String?
    val name: String?
    // economy
    val moneyPerMessage: Double?
    val slavePrice: Double?
    val slaveMoneyPerSecond: Double?
    // roles
    val roles: Array<Role>

    var id: String? = null

    init {
      val gson = Gson()
      val botProperties = gson.fromJson(
          createJsonString("bot"),
          object : TypeToken<HashMap<String, String>>() {}.type
      ) as HashMap<String, String>
      val economyProperties = gson.fromJson(
          createJsonString("economy"),
          object : TypeToken<HashMap<String, Double>>() {}.type
      ) as HashMap<String, Double>

      roles = gson.fromJson(
          createJsonString("roles"),
          object : TypeToken<Array<Role>>() {}.type
      ) as Array<Role>

      roles.forEach { println(it.toString()) }

      token = botProperties["token"]
      name = botProperties["name"]

      moneyPerMessage = economyProperties["moneyPerMessage"]
      slavePrice = economyProperties["slavePrice"]
      slaveMoneyPerSecond = economyProperties["slaveMoneyPerSecond"]

      validateProperties()
    }

    /**
     * Reads and transforms .json file into string of provided category
     * @param propertiesCategory bot / economy / roles or any other file if you created such.
     */
    private fun createJsonString(propertiesCategory: String): String {
      val classloader = Thread.currentThread().contextClassLoader
      val inputStream = classloader.getResourceAsStream("$propertiesCategory.json")
          ?: throw IllegalArgumentException(
              "Please provide resources/$propertiesCategory.json file."
          )
      return String(inputStream.readAllBytes())
    }

    /**
     * Function that throws exception if you didnt provide or
     * provided wrong parameters in .json files
     */
    private fun validateProperties() {
      val nullMembers = ArrayList<String>(3)
      if (token == null) nullMembers.add("token")
      if (name == null) nullMembers.add("name")
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
  }
}
