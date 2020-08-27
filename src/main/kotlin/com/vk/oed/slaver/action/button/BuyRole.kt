package com.vk.oed.slaver.action.button

import com.vk.oed.slaver.action.ButtonData
import com.vk.oed.slaver.action.RpgButton
import com.vk.oed.slaver.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BuyRole
@Autowired constructor(
    playerService: PlayerService,
) : RpgButton(playerService) {

  override val trigger: String =
      "slaver role shop"

  override fun execute(buttonData: ButtonData) {
    TODO("implement")
  }
}