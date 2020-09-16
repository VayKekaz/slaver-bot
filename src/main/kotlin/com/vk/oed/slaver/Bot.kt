package com.vk.oed.slaver

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.vk.oed.slaver.listener.ButtonListener
import com.vk.oed.slaver.listener.MessageListener
import com.vk.oed.slaver.model.Dungeon
import com.vk.oed.slaver.model.RpgRole
import com.vk.oed.slaver.util.DurationDeserializer
import dev.minn.jda.ktx.injectKTX
import dev.minn.jda.ktx.listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.ReadyEvent
import org.hibernate.PropertyNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Duration
import kotlin.concurrent.thread
import kotlin.random.Random

@Component
class Bot
@Autowired constructor(
    messageListener: MessageListener,
    buttonListener: ButtonListener,
) {

  init {
    jda.listener(messageListener::onMessageReceived)
    jda.listener(buttonListener::onMessageReactionAdd)
  }

  companion object Properties {
    // bot.json
    val token: String
    val name: String

    // economy.json
    val moneyPerMessage: Double
    val slavePrice: Double
    val slaveMoneyPerSecond: Double

    // roles.json
    val rpgRoles: Array<RpgRole>

    // dungeons.json
    val dungeons: Array<Dungeon>

    val jda: JDA

    val avatarUrl: String?
    val id: String
    val guild: Guild

    val random: Random

    init {
      val gson = GsonBuilder()
          .registerTypeAdapter(Duration::class.java, DurationDeserializer())
          .create()
      val botProperties: HashMap<String, String> =
          gson.fromJsonAsType(createJsonString("bot"))
      val economyProperties: HashMap<String, Double> =
          gson.fromJsonAsType(createJsonString("economy"))

      rpgRoles =
          gson.fromJsonAsType(createJsonString("roles"))
      dungeons =
          gson.fromJsonAsType(createJsonString("dungeons"))
      dungeons.sortBy { -(it.minBounty + it.maxBounty) / 2 }

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
      jda = builder.build()
      jda.listener<ReadyEvent> { thread { validateBot() } }
      jda.awaitReady()

      val selfUser = jda.selfUser
      id = selfUser.id
      avatarUrl = selfUser.avatarUrl
      guild = jda.guilds[0]

      random = Random.Default
    } // init

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

    private inline fun <reified T> Gson.fromJsonAsType(jsonString: String): T =
        fromJson(jsonString, object : TypeToken<T>() {}.type)
  }
}
