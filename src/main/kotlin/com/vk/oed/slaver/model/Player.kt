package com.vk.oed.slaver.model

import net.dv8tion.jda.api.entities.User
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Player(
  @Id
  var id: String,
  var money: Double,
  var slaves: Long
) {

  constructor(user: User) : this(
    id = user.id,
    money = 0.0,
    slaves = 0
  )
}