package com.vk.oed.slaver.service

import com.vk.oed.slaver.db.PlayerRepository
import com.vk.oed.slaver.model.Player
import net.dv8tion.jda.api.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlayerService(@Autowired val repository: PlayerRepository) {

  fun getPlayerBy(user: User): Player? =
      repository.findByIdOrNull(user.id)

  fun addAmountOfMoneyToUser(amount: Long, user: User) {
    val player = getPlayerBy(user) ?: Player(user)
    player.receiveMoney(amount)
    repository.save(player)
  }
}