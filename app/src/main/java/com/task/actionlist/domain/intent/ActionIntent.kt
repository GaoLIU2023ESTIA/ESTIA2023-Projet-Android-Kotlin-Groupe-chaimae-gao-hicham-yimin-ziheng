package com.task.actionlist.domain.intent

import com.task.actionlist.data.model.ActionListItem


sealed class ActionIntent {

    data class GetList(
        val actions: List<ActionListItem>? = null
    ) : ActionIntent()

    data class RemoveItem(
        val param: ActionListItem? = null,
        val isSuccess: Boolean = true
    ) : ActionIntent()


    data class MarkItem(
        val param: ActionListItem? = null,
        val isSuccess: Boolean = true
    ) : ActionIntent()


    data class AddItem(
        val param: ActionListItem? = null,
        val isSuccess: Boolean = true
    ) : ActionIntent()
}
