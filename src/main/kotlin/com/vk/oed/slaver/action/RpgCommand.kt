package com.vk.oed.slaver.action

import com.vk.oed.slaver.service.PlayerService

abstract class RpgCommand(
    playerService: PlayerService
) : RpgAction(playerService), Command