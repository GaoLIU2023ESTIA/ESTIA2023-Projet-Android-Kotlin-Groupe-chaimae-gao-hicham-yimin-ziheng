package com.task.actionlist

import android.content.Context
import androidx.room.*
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.task.actionlist.data.db.ActionDao
import com.task.actionlist.data.db.ActionDatabase
import com.task.actionlist.data.model.ActionListItem
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var actionDao: ActionDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context,
            ActionDatabase::class.java
        ).build()
        actionDao = db.actionDao()

    }

    @Test
    fun getAll() {
        runBlocking {
            actionDao.insert(ActionListItem().copy(_id = "1"))
            assertTrue(actionDao.getAll().firstOrNull() != null)
        }
    }


    @Test
    fun insert() {
        runBlocking {
            val insert = actionDao.insert(ActionListItem().copy(_id = "2"))
            assertTrue(insert > 0)
        }
    }


    @Test
    fun update() {
        runBlocking {
            val copy = ActionListItem().copy(localId = 10, _id = "2")
            actionDao.insert(copy)
            copy.completed = true
            assertTrue(actionDao.update(copy) > 0)
        }
    }

    @Test
    fun delete() {
        runBlocking {
            val copy = ActionListItem().copy(localId = 20, _id = "2")
            actionDao.insert(copy)
            assertTrue(actionDao.delete(copy) > 0)
        }
    }


    @Test
    fun deleteAll() {
        actionDao.deleteAll()
        runBlocking {
            assertTrue(actionDao.getAll().firstOrNull() != null)
        }
    }


}