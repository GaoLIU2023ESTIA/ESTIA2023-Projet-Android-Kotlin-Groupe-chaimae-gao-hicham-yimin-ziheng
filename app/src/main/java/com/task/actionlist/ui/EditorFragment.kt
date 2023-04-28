package com.task.actionlist.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import com.blankj.utilcode.util.ToastUtils
import com.kunminx.architecture.ui.page.DataBindingConfig
import com.kunminx.architecture.ui.state.State
import com.task.actionlist.App
import com.task.actionlist.BR
import com.task.actionlist.R
import com.task.actionlist.data.model.ActionListItem
import com.task.actionlist.domain.intent.ActionIntent
import com.task.actionlist.domain.intent.Messages
import com.task.actionlist.domain.message.PageMessenger
import com.task.actionlist.domain.request.ActionRequester
import com.task.actionlist.ui.bind.ClickProxy
import com.task.actionlist.utils.TimeUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class EditorFragment : BaseFragment() {
    private val states by viewModels<EditorStates>()
    private val noteRequester by viewModels<ActionRequester>()
    private val messenger by activityViewModels<PageMessenger>()
    private val clickProxy by lazy { ClickProxy() }
    private var isEdit = false

    override fun getDataBindingConfig(): DataBindingConfig {
        return DataBindingConfig(R.layout.fragment_editor, BR.state, states)
            .addBindingParam(BR.click, clickProxy)
    }

    override fun onInitData() {
        if (arguments != null) {
            val parcelable = requireArguments().getParcelable<ActionListItem>(ACTION)
            isEdit = parcelable!!.localId > 0
            Log.i("TAG", "onInitData: $isEdit")
            if (isEdit) {
                states.tempNote.set(parcelable)
                states.tempNote.get()?.let {
                    states.title.set(it.task)
                    states.content.set(it.description)
                    states.assignee.set(it.assignee)
                    states.requestedBy.set(it.requested_by)
//                    if (TextUtils.isEmpty(it.localId)) {
//                        states.titleRequestFocus.set(true)
//                    } else {
                    states.tip.set(getString(R.string.last_time_modify))
                    states.time.set(it.last_updated)
//                    }
                }
                states.visible.set(false)
            }
        }
    }


    override fun onOutput() {
        noteRequester.output(this) { event ->
            states.loading.set(false)
            if (event is ActionIntent.AddItem) {
                messenger.input(Messages.RefreshNoteList)
                shortToast(getString(R.string.saved))
                nav().navigateUp()
            }
        }
    }

    override fun onInput() {
        clickProxy.setOnClickListener { v ->
            when (v.id) {
                R.id.fab -> save()
                R.id.btn_back -> nav().navigateUp()
                R.id.btn_calendar -> {
                    // 弹出对话框
                    val calendar = Calendar.getInstance()
                    val datePickerDialog = DatePickerDialog(
                        requireContext(),
                        { _, year, month, dayOfMonth ->
                            val s = "$year-${month + 1}-$dayOfMonth"
                            states.dueDate.set(s)
                            ToastUtils.showShort(s)
                            Log.i("TAG", "datePickerDialog: $s")
                            states.time.set("due date:$s")
                        },
                        calendar[Calendar.YEAR],
                        calendar[Calendar.MONTH],
                        calendar[Calendar.DAY_OF_MONTH]
                    )
                    datePickerDialog.show()
                }
            }
        }
    }

    private fun save() {
        val tempNote = states.tempNote.get()
        val title = states.title.get()
        val content = states.content.get()
        val assignee = states.assignee.get()
        val requestedBy = states.requestedBy.get()

        val empty = TextUtils.isEmpty(title) || TextUtils.isEmpty(content) ||
                TextUtils.isEmpty(assignee) || TextUtils.isEmpty(requestedBy)
        // TODO: 没有修改功能
//        val unChanged = tempNote?.task == title && tempNote?.description == content&&
//                tempNote?.assignee == assignee && tempNote?.requested_by == requestedBy
//        if (empty || unChanged) {
//            shortToast(getString(R.string.please_complete))
//            return
//        }
        if (empty) {
            shortToast(getString(R.string.please_complete))
            return
        }
        states.loading.set(true)
        val time = TimeUtils.getTime(System.currentTimeMillis(), TimeUtils.YYYY_MM_DD_HH_MM_SS)

        val action = if (isEdit) {
            ActionListItem(
                tempNote!!.localId,
                tempNote._id,
                tempNote.assignee,
                tempNote.completed,
                content!!,
                tempNote.due_date,
                time,
                tempNote.requested_by,
                title!!
            )
        } else {
            isEdit = true
            val dueDate = states.dueDate.get()!!
            ActionListItem(
                0,
                "",
                assignee!!,
                false,
                content!!,
                dueDate.ifEmpty { time },
                time,
                requestedBy!!,
                title!!
            )
        }
        noteRequester.input(ActionIntent.AddItem(param = action))
    }

    class EditorStates : StateHolder() {
        val tempNote = State<ActionListItem>(ActionListItem())
        val title = State("")
        val content = State("")
        val assignee = State("")
        val requestedBy = State("")
        val dueDate = State("")
        val tip: State<String> = State(App.sApplication?.getString(R.string.edit)!!)
        val time: State<String> = State(App.sApplication?.getString(R.string.new_note)!!)
        val titleRequestFocus = State(false)
        val visible = State(true)
        val loading = State(false)
    }

    companion object {
        private const val ACTION = "ACTION"
        fun start(controller: NavController, action: ActionListItem?) {
            val bundle = Bundle()
            bundle.putParcelable(ACTION, action)
            controller.navigate(R.id.action_ListFragment_to_EditorFragment, bundle)
        }
    }
}