package com.vk.oed.slaver.service

import com.vk.oed.slaver.Bot
import com.vk.oed.slaver.repository.PlayerRepository
import com.vk.oed.slaver.model.Player
import net.dv8tion.jda.api.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class PlayerService @Autowired constructor(
    private val repository: PlayerRepository
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

  fun save(player: Player) =
      repository.save(player)

  fun addAmountOfMoneyToUser(amount: Double, user: User) {
    val player = getPlayerBy(user)
    player.money += amount
    repository.save(player)
    return
    TODO("create new ways to update users by custom @Query")
  }

  fun getUpdatedPlayerBy(user: User): Player {
    val player = getPlayerBy(user)
    player.updateBalance()
    return player
  }
}