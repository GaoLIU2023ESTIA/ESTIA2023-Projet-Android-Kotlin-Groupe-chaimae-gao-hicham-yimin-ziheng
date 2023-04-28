package com.task.actionlist.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.actionlist.data.model.ActionListItem

@Database(entities = [ActionListItem::class], version = 1, exportSchema = false)
abstract class ActionDatabase : RoomDatabase() {
    abstract fun actionDao(): ActionDao
}