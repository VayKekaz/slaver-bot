package com.vk.oed.slaver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.oed.slaver.listener.ButtonListener
import com.vk.oed.slaver.listener.MessageListener
import com.vk.oed.slaver.model.RpgRole
import dev.minn.jda.ktx.injectKTX
import dev.minn.jda.ktx.listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import org.hibernate.PropertyNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.NullPointerException
import kotlin.concurrent.thread

@Component
class Bot
@Autowired constructor(
    messageListener: MessageListener,
    buttonListener: ButtonListener,
) {

  init {
    jda.listener<ReadyEvent> { thread { validateBot() } }
    jda.listener<MessageReceivedEvent> { messageListener.onMessageReceived(it) }
    jda.listener<MessageReactionAddEvent> { buttonListener.onMessageReactionAdd(it) }
  }

  companion object Properties {
    // bot.json
    val token: String
    val name: String

    // economy.json
    val moneyPerMessage: Double
    val slavePrice: Double
    val slaveMoneyPerSecond: Double

    val id: String

    // roles.json
    val rpgRoles: Array<RpgRole>

    val jda: JDA
    val guild: Guild

    init {
      val gson = Gson()
      val botProperties: HashMap<String, String> =
          gson.fromJson(
              createJsonString("bot"),
              object : TypeToken<HashMap<String, String>>() {}.type
          )
      val economyProperties: HashMap<String, Double> =
          gson.fromJson(
              createJsonString("economy"),
              object : TypeToken<HashMap<String, Double>>() {}.type
          )

      rpgRoles =
          gson.fromJson(
              createJsonString("roles"),
              object : TypeToken<Array<RpgRole>>() {}.type
          )

      try {
        token = botProperties["token"]!!
        name = botProperties["name"]!!

        moneyPerMessage = economyProperties["moneyPerMessage"]!!
        slavePrice = economyProperties["slavePrice"]!!
        slaveMoneyPerSecond = economyProperties["slaveMoneyPerSecond"]!!

      } catch (exception: NullPointerException) {
        throw PropertyNotFoundException("Please provide correct properties.")
      }

      val builder = JDABuilder.createDefault(token)
      builder.injectKTX()
      jda = builder.build().awaitReady()

      id = jda.selfUser.id

      guild = jda.guilds[0]
    }

    /**
     * Reads and transforms .json file into string of provided category.
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
  }
}
