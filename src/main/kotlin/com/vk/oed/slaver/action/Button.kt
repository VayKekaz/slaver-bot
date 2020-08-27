package com.vk.oed.slaver.action

interface Button {

  val trigger: String

  fun triggeredBy() {
    TODO("trigger by footer")
  }

  fun execute(buttonData: ButtonData)
}