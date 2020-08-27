package com.vk.oed.slaver

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.oed.slaver.listener.ButtonListener
import com.vk.oed.slaver.listener.MessageListener
import com.vk.oed.slaver.listener.OnReadyValidator
import com.vk.oed.slaver.model.Role
import dev.minn.jda.ktx.injectKTX
import dev.minn.jda.ktx.listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class Bot
@Autowired constructor(
    messageListener: MessageListener,
    buttonListener: ButtonListener,
) {

  private val jda: JDA

  init {
    val builder = JDABuilder.createDefault(token)
    builder.injectKTX()
    builder.addEventListeners(
        messageListener,
        OnReadyValidator(),
    )
    jda = builder.build()
    jda.listener<ReadyEvent> {
      thread { OnReadyValidator().onReady(it) }
    }
    jda.listener<MessageReceivedEvent> { messageListener.onMessageReceived(it) }
    jda.listener<MessageReactionAddEvent> { buttonListener.onMessageReactionAdd(it) }

    id = jda.selfUser.id
  }

  @Bean
  fun jda(): JDA = jda

  companion object Properties {
    // bot.json
    val token: String?
    val name: String?

    // economy.json
    val moneyPerMessage: Double?
    val slavePrice: Double?
    val slaveMoneyPerSecond: Double?

    // roles.json
    val roles: Array<Role>

    var id: String? = null

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

      roles =
          gson.fromJson(
              createJsonString("roles"),
              object : TypeToken<Array<Role>>() {}.type
          )

      token = botProperties["token"]
      name = botProperties["name"]

      moneyPerMessage = economyProperties["moneyPerMessage"]
      slavePrice = economyProperties["slavePrice"]
      slaveMoneyPerSecond = economyProperties["slaveMoneyPerSecond"]
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
