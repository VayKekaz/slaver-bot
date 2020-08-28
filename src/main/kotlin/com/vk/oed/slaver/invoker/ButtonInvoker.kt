package com.vk.oed.slaver.invoker

import com.vk.oed.slaver.action.Button
import com.vk.oed.slaver.action.ButtonData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ButtonInvoker
@Autowired constructor(
    val buttonActions: Array<out Button>
) {

  fun invoke(buttonData: ButtonData) {
    findMatchingAction(buttonData)?.execute(buttonData)
  }

  private fun findMatchingAction(buttonData: ButtonData): Button? =
      buttonActions.firstOrNull { it.triggeredBy(buttonData) }
}
