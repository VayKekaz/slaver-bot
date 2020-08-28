package com.vk.oed.slaver.action

interface Button {

  val trigger: String

  fun triggeredBy(buttonData: ButtonData): Boolean =
      this.trigger == buttonData.marker

  fun execute(buttonData: ButtonData)
}