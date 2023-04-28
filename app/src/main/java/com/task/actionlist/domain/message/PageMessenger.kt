package com.task.actionlist.domain.message

import com.kunminx.architecture.domain.dispatch.MviDispatcherKTX
import com.task.actionlist.domain.intent.Messages

class PageMessenger : MviDispatcherKTX<Messages>() {
  override suspend fun onHandle(event: Messages) {
    sendResult(event)
  }
}
