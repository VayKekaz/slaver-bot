package com.vk.oed.slaver.model

import com.vk.oed.slaver.Bot
import net.dv8tion.jda.api.entities.User
import java.time.Duration
import java.time.Instant
import javax.persistence.*
import kotlin.jvm.Throws

@Entity
class Player
constructor(
    @Id
    var id: String,
    var money: Double,
    var slaves: Long,
    var lastBalanceUpdate: Instant,
    var sortieStart: Instant? = null,
    @ManyToOne
    var dungeon: Dungeon? = null,
    @Embedded
    var rpgRole: RpgRole? = null,
) {


  val isInSortie: Boolean
    get() {
      if (sortieStart == null || dungeon == null) return false
      val sortieEnd = sortieStart!!.plus(dungeon!!.sortieDuration)
      val now = Instant.now()
      return sortieEnd.isBefore(now)
    }

  constructor(user: User) : this(
      id = user.id,
      money = 0.0,
      slaves = 0,
      lastBalanceUpdate = Instant.now(),
  )

  fun updateBalance() {
    val now = Instant.now()
    val timeDifference = Duration.between(lastBalanceUpdate, now).abs().toSeconds()
    money += slaves * Bot.slaveMoneyPerSecond * timeDifference
    lastBalanceUpdate = now
  }

  @Throws(IllegalStateException::class)
  fun startSortie(dungeon: Dungeon) {
    if (isInSortie)
      throw IllegalStateException("You must not be in sortie to end it.")
    sortieStart = Instant.now()
    this.dungeon = dungeon
  }

  @Throws(IllegalStateException::class)
  fun endSortieAndGetBounty(): Double {
    if (!isInSortie)
      throw IllegalStateException("You must be in sortie to end it.")
    val bounty = dungeon!!.createRandomBounty()
    money += bounty
    this.dungeon = null
    this.sortieStart = null
    return bounty
  }
}