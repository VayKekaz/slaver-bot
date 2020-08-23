package com.vk.oed.slaver.model

import net.dv8tion.jda.api.entities.User
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
    var role: Role?
) {

  constructor(user: User) : this(
      id = user.id,
      money = 0.0,
      slaves = 0,
      lastBalanceUpdate = Instant.now(),
      role = null
  )
}