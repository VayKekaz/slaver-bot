package com.vk.oed.slaver.db

import com.vk.oed.slaver.model.Player
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : CrudRepository<Player, String>
