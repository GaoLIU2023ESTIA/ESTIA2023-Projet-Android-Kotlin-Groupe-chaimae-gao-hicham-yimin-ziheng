package com.task.actionlist.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.task.actionlist.R
import kotlinx.parcelize.Parcelize

@Entity(tableName = "action")
@Parcelize
data class ActionListItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "localId")
    val localId: Long,
    @ColumnInfo(name = "_id")
    val _id: String,
    val assignee: String,
    var completed: Boolean,
    val description: String,
    @ColumnInfo(name = "create_time")
    val due_date: String,
    @ColumnInfo(name = "modify_time")
    val last_updated: String,
    val requested_by: String,
    val task: String
) : Parcelable {
    @Ignore
    constructor() : this(0,"", "", false, "", "", "", "", "")


    @get:Ignore
    val isMarked: Boolean
        get() = completed


    @Ignore
    fun markIcon(): Int = if (isMarked) R.drawable.icon_complete else R.drawable.icon_undone

    fun copy(): ActionListItem {
        return ActionListItem(
            localId,
            _id,
            description,
            completed,
            last_updated,
            due_date,
            last_updated,
            requested_by,
            task
        )
    }

    override fun toString(): String {
        return "ActionListItem(localId=$localId, _id='$_id', assignee='$assignee', completed=$completed, description='$description', due_date='$due_date', last_updated='$last_updated', requested_by='$requested_by', task='$task', isMarked=$isMarked)"
    }

}