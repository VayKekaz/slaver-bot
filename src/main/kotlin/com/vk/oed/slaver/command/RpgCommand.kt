package com.vk.oed.slaver.command

import com.vk.oed.slaver.service.PlayerService

abstract class RpgCommand(
    internal val playerService: PlayerService
) : Command
