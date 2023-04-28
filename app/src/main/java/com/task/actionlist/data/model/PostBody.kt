package com.task.actionlist.data.model

data class PostBody(
    val assignee: String,
    val description: String,
    val due_date: String,
    val requested_by: String,
    val task: String,
    val completed:Boolean
)