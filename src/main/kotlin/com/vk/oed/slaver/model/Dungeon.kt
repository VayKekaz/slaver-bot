package com.vk.oed.slaver.model

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.util.dollarEmote
import com.vk.oed.slaver.util.moneybagEmote
import java.time.Duration
import javax.persistence.Entity
import javax.persistence.Id
import kotlin.math.pow
import kotlin.math.roundToInt

@Entity
class Dungeon
private constructor(
    @Id val name: String,
    val price: Double,
    val minBounty: Double,
    val maxBounty: Double,
    val sortieDuration: Duration,
) {

  fun createRandomBounty(): Double {
    val bountyDifference = maxBounty - minBounty
    val bountyMultiplier = Bot.random.nextDouble().pow(2)
    return bountyDifference * bountyMultiplier + minBounty
  }

  fun asShopItem(): String =
      "$name **price: $price $dollarEmote** " +
          "**bounty: ${minBounty.roundToInt()} - ${maxBounty.roundToInt()} $moneybagEmote**"
}