package com.vk.oed.slaver.action

import com.vk.oed.slaver.service.PlayerService

abstract class RpgButton(
    playerService: PlayerService
) : RpgAction(playerService), Button