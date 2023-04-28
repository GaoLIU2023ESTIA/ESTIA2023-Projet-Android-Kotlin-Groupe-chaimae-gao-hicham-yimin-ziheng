package com.task.actionlist.domain.intent

sealed class Messages {
  object RefreshNoteList : Messages()
  object FinishActivity : Messages()
}
