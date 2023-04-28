package com.task.actionlist.data.db

import androidx.room.*
import com.task.actionlist.data.model.ActionList
import com.task.actionlist.data.model.ActionListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ActionDao {
    @Query("select * from `action`")
    fun getAll(): Flow<List<ActionListItem>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(action: ActionListItem): Long


    @Update
    suspend fun update(action: ActionListItem): Int

    @Delete
    fun delete(action: ActionListItem): Int


    @Query("DELETE FROM `action`")
    fun deleteAll()

}
