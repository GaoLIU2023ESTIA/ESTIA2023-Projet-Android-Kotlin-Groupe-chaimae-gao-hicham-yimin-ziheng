package com.task.actionlist.ui

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.state.State
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.task.actionlist.BR
import com.task.actionlist.R
import com.task.actionlist.data.model.ActionListItem
import com.task.actionlist.domain.intent.ActionIntent
import com.task.actionlist.domain.intent.Messages
import com.task.actionlist.domain.message.PageMessenger
import com.task.actionlist.domain.request.ActionRequester
import com.task.actionlist.ui.adapter.ActionAdapter
import com.task.actionlist.ui.bind.ClickProxy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment() {
    private val states by viewModels<ListStates>()
    private val actionRequester by viewModels<ActionRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private val adapter by lazy { ActionAdapter(states.list) }
    private val clickProxy by lazy { ClickProxy() }
    private val refreshListener by lazy {
        OnRefreshListener {
            Log.i("TAG", "refreshListener: ")
            if (it.isRefreshing) {
                states.refresh.set(true)
                actionRequester.input(ActionIntent.GetList())
            }
        }
    }

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_list, BR.state, states)
            .addBindingParam(BR.adapter, adapter)
            .addBindingParam(BR.click, clickProxy)
            .addBindingParam(BR.refreshListener, refreshListener)
    }


    override fun onOutput() {

        messenger.output(this) { messages ->
            if (messages is Messages.RefreshNoteList) actionRequester.input(ActionIntent.GetList())
        }
        actionRequester.output(this) { event ->
            when (event) {
                is ActionIntent.GetList -> {
                    Log.i("TAG", "onOutput: ${event.actions?.size}")
                    states.refresh.set(false)
                    adapter.refresh(event.actions!!)
                    states.emptyViewShow.set(states.list.isEmpty())
                }
                is ActionIntent.MarkItem -> {
                    Log.i("TAG", "onOutput: ${event.isSuccess}")
                }
                is ActionIntent.RemoveItem -> {}
                else -> {}
            }
        }
    }


    override fun onInput() {
        adapter.setOnItemClick { viewId, item, position ->
            when (viewId) {
                R.id.btn_mark -> {
                    item.completed = item.completed.not()
                    actionRequester.input(
                        ActionIntent.MarkItem(
                            param = item.copy()
                        )
                    )
                    adapter.notifyItemChanged(position)
                }
                R.id.btn_delete -> actionRequester.input(ActionIntent.RemoveItem(param = item))
                R.id.cl -> EditorFragment.start(nav(), item)
            }
        }
        clickProxy.setOnClickListener { view ->
            if (view.id == R.id.fab) EditorFragment.start(nav(), ActionListItem())
        }
        if (states.list.isEmpty()) actionRequester.input(ActionIntent.GetList())
    }

    class ListStates : StateHolder() {
        val list = mutableListOf<ActionListItem>()
        val emptyViewShow = State(false)
        val refresh = State(false)
    }


}