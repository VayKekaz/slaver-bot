package com.vk.oed.slaver.model

import net.dv8tion.jda.api.entities.User
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Player(
    @Id
    var id: String? = null,
    var money: Long? = null,
    var slaves: Long? = null

) {
  constructor(user: User) : this() {
    this.id = user.id
    this.money = 0
    this.slaves = 0
  }

  fun receiveMoney(amount: Long) {
    this.money = this.money?.plus(amount)
  }

  fun buySlavesInQuantity(slaveNumber: Long) {
    slaves = slaves?.plus(slaveNumber)
  }
}