package com.task.actionlist.data.repo

import android.util.Log
import com.task.actionlist.App
import com.task.actionlist.data.db.ActionDao
import com.task.actionlist.data.model.ActionListItem
import com.task.actionlist.data.model.PostBody
import com.task.actionlist.data.model.PostStatus
import com.task.actionlist.data.network.RetrofitNetwork
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val actionDao: ActionDao,
    private val network: RetrofitNetwork
) {
    private val mmkv = MMKV.defaultMMKV()

    suspend fun getNotes(): Flow<List<ActionListItem>> {
        return withContext(Dispatchers.IO) {
            Log.i("TAG", "getNotes:${App.isConnect} ")
            if (App.isConnect) {
                var postAction = network.getActionList().single()
                //.catch { emit(ActionList()) }
//                if (postAction.isEmpty()) {
//                    return@withContext actionDao.getAll()
//                }

                if (mmkv.decodeBool("edit", false)) {
                    mmkv.encode("edit", false)
                    var mark = false
                    Log.i("TAG", "getNotes_edit: ")
                    //4. Synchroniser les opérations locales avec celles du serveur lorsque la connexion Internet est rétablie, en respectant les restrictions suivantes.
                    //- Si une tâche a été supprimée sur le serveur, elle ne doit pas être recréée (c'est-à-dire supprimée localement).
                    val localAll =
                        actionDao.getAll().firstOrNull()?.toMutableList() ?: mutableListOf()

                    postAction.forEach {
                        val local = localAll.find { local -> local._id == it._id }
                        if (local == null) {
                            mark = true
                            val deleteAction = network.deleteAction(it._id).single()

                            Log.i(
                                "TAG",
                                "getNotes_deleteAction:${it.task} ${it._id} ${deleteAction.status}"
                            )
                        } else if (local.completed != it.completed) {
                            mark = true
                            val single =
                                network.putAction(it._id, if (local.completed) 1 else 0).single()
                            Log.i(
                                "TAG",
                                "getNotes_putAction:${it.task} ${local.completed} ${single.status} "
                            )
                            localAll.remove(local)
                        } else {
                            Log.i("TAG", "getNotes_remove:${it.task} ")
                            localAll.remove(local)
                        }
                    }

                    localAll.forEach { action ->
                        mark = true
                        Log.i("TAG", "getNotes_postAction: $action ")
                        val single = network.postAction(
                            PostBody(
                                action.assignee,
                                action.description,
                                action.due_date,
                                action.requested_by,
                                action.task,
                                action.completed
                            )
                        ).single()
                        Log.i("TAG", "getNotes_postAction2: ${single.status} ")
                    }

                    Log.i("TAG", "getNotes_mark:$mark ")
                    if (mark) postAction = network.getActionList().single()
                }

                Log.i("TAG", "getNotes: ${postAction.size}")
                actionDao.deleteAll()
                postAction.forEach {
                    actionDao.insert(it)
                }
                return@withContext actionDao.getAll()
            } else {
                return@withContext actionDao.getAll()
            }
        }
    }

    suspend fun insertAction(action: ActionListItem): Boolean {
        return withContext(Dispatchers.IO) {
            if (App.isConnect) {
                val postAction = network.postAction(
                    PostBody(
                        action.assignee,
                        action.description,
                        action.due_date,
                        action.requested_by,
                        action.task,
                        false
                    )
                ).catch { emit(PostStatus("", "404")) }.single()
                if (postAction.status == "200") {
                    actionDao.insert(action)
                    return@withContext true
                }
                return@withContext false
            } else {
                mmkv.encode("edit", true)
                return@withContext actionDao.insert(action) > 0
            }
        }
    }

    suspend fun putAction(action: ActionListItem): Boolean {
        return withContext(Dispatchers.IO) {
            if (App.isConnect) {
                val postAction =
                    network.putAction(action._id, if (action.completed) 1 else 0)
                        .catch { emit(PostStatus("", "Fail")) }.single()
                if (postAction.status == "OK") {
                    actionDao.update(action)
                    return@withContext true
                }
                return@withContext false
            } else {
                mmkv.encode("edit", true)
                return@withContext actionDao.update(action) > 0
            }
        }
    }

    suspend fun deleteNote(action: ActionListItem): Boolean {
        return withContext(Dispatchers.IO) {
            if (App.isConnect) {
                val postAction =
                    network.deleteAction(action._id).catch { emit(PostStatus("", "Fail")) }.single()
                if (postAction.status == "OK") {
                    actionDao.delete(action)
                    return@withContext true
                }
                return@withContext false
            } else {
                mmkv.encode("edit", true)
                return@withContext actionDao.delete(action) > 0
            }
        }
    }


}