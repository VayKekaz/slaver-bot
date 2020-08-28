package com.vk.oed.slaver.model

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.entities.User
import java.time.Duration
import java.time.Instant
import javax.persistence.*

@Entity
class Player constructor(
    @Id
    var id: String,
    var money: Double,
    var slaves: Long,
    var lastBalanceUpdate: Instant,
    @Embedded
    var rpgRole: RpgRole?
) {

  constructor(user: User) : this(
      id = user.id,
      money = 0.0,
      slaves = 0,
      lastBalanceUpdate = Instant.now(),
      rpgRole = null
  )

  fun updateBalance() {
    val now = Instant.now()
    val timeDifference = Duration.between(lastBalanceUpdate, now).abs().toSeconds()

    money += slaves * Bot.slaveMoneyPerSecond * timeDifference
    lastBalanceUpdate = now
  }

  fun updatedNow() {
    lastBalanceUpdate = Instant.now()
  }
}