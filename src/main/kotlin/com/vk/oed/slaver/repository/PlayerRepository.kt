package com.vk.oed.slaver.repository

import com.vk.oed.slaver.model.Player
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PlayerRepository : CrudRepository<Player, String>
