package com.vk.oed.slaver.service

import com.vk.oed.slaver.db.PlayerRepository
import com.vk.oed.slaver.model.Player
import net.dv8tion.jda.api.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PlayerService @Autowired constructor(
    val repository: PlayerRepository
) {
  fun getPlayerBy(user: User): Player {
    return repository.findByIdOrNull(user.id)
        ?: createAndReturn(user)
  }

  fun createAndReturn(user: User): Player {
    val player = Player(user)
    repository.save(player)
    return player
  }

  fun addAmountOfMoneyToUser(amount: Double, user: User) {
    val player = getPlayerBy(user)
    player.money += amount
    repository.save(player)
  }

  fun subtractAmountOfMoneyFromUser(amount: Double, user: User) {
    val player = getPlayerBy(user)
    player.money += amount
    repository.save(player)
  }

  fun save(player: Player) =
      repository.save(player)
}