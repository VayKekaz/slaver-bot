package com.vk.oed.slaver

import com.vk.oed.slaver.listener.MessageListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Bot(
    @Autowired val messageListener: MessageListener
) {
  private val jda: JDA

  companion object {
    const val id = "745015437116571659"
  }

  init {
    val builder = JDABuilder.createDefault(token)
    builder.addEventListeners(messageListener)

    jda = builder.build()
  }

  private val token: String
    get() {
      val classloader = Thread.currentThread().contextClassLoader
      val inputStream = classloader.getResourceAsStream("token")
      return String(
          inputStream?.readAllBytes()
              ?: throw IllegalArgumentException("Please provide token in resources/token file")
      ).trim()
    }
}
