package com.vk.oed.slaver.exception

class LowBalanceException : RuntimeException {

  constructor() : super()
  constructor(message: String) : super(message)
}