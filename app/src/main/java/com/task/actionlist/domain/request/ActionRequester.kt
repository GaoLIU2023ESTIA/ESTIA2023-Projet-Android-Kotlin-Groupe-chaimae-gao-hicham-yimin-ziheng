package com.task.actionlist.domain.request

import com.kunminx.architecture.domain.dispatch.MviDispatcherKTX
import com.task.actionlist.data.repo.DataRepository
import com.task.actionlist.domain.intent.ActionIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class ActionRequester @Inject constructor(
    private val repository: DataRepository,
) :
    MviDispatcherKTX<ActionIntent>() {

    override suspend fun onHandle(event: ActionIntent) {
        when (event) {
            is ActionIntent.MarkItem -> sendResult(
                event.copy(
                    isSuccess = repository.putAction(event.param!!)
                )
            )
            is ActionIntent.AddItem -> sendResult(
                event.copy(
                    isSuccess = repository.insertAction(
                        event.param!!
                    )
                )
            )
            is ActionIntent.RemoveItem -> sendResult(
                event.copy(
                    isSuccess = repository.deleteNote(
                        event.param!!
                    )
                )
            )
            is ActionIntent.GetList -> sendResult(
                event.copy(
                    repository.getNotes().firstOrNull()?.sortedByDescending { it.last_updated }
                )
            )
        }
    }
}
