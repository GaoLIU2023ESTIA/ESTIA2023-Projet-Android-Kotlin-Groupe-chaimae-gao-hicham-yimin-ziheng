package com.task.actionlist.ui.bind

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.kunminx.architecture.ui.state.State
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshListener
import com.task.actionlist.App

object BindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["visible"], requireAll = false)
    fun visible(view: View, visible: Boolean) {
        if (visible && view.visibility == View.GONE) {
            view.visibility = View.VISIBLE
        } else if (!visible && view.visibility == View.VISIBLE) {
            view.visibility = View.GONE
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["invisible"], requireAll = false)
    fun invisible(view: View, visible: Boolean) {
        if (visible && view.visibility == View.INVISIBLE) {
            view.visibility = View.VISIBLE
        } else if (!visible && view.visibility == View.VISIBLE) {
            view.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["imgRes"], requireAll = false)
    fun setImageResource(imageView: ImageView, imgRes: Int) {
        imageView.setImageResource(imgRes)
    }

    @JvmStatic
    @BindingAdapter(value = ["addItemDecoration"], requireAll = false)
    fun addItemDecoration(recyclerView: RecyclerView, int: Int) {
        //添加Android自带的分割线
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                if (int == 1) DividerItemDecoration.VERTICAL else DividerItemDecoration.HORIZONTAL
            )
        )
    }

    @JvmStatic
    @BindingAdapter(value = ["refreshListener"], requireAll = false)
    fun refreshListener(smartRefreshLayout: SmartRefreshLayout, l: OnRefreshListener) {
        Log.i("TAG", "refreshStatus: ")
//        if (state.get() == false) smartRefreshLayout.finishRefresh()
//        val l = OnRefreshListener { TODO("Not yet implemented") }
        smartRefreshLayout.setOnRefreshListener(l)
//        smartRefreshLayout.setOnRefreshListener {
//            Log.i("TAG", "refreshStatus2 ")
//            if (it.isRefreshing) state.set(true)
//        }
    }
    @JvmStatic
    @BindingAdapter(value = ["finishRefresh"], requireAll = false)
    fun finishRefresh(smartRefreshLayout: SmartRefreshLayout, boolean: Boolean) {
        Log.i("TAG", "finishRefresh: ")
        if (!boolean)smartRefreshLayout.finishRefresh()
    }

    @JvmStatic
    @BindingAdapter(value = ["textColor"], requireAll = false)
    fun setTextColor(textView: TextView, textColorRes: Int) {
        textView.setTextColor(textView.context.getColor(textColorRes))
    }

    @JvmStatic
    @BindingAdapter(value = ["selected"], requireAll = false)
    fun selected(view: View, select: Boolean) {
        view.isSelected = select
    }

    @JvmStatic
    @BindingAdapter(value = ["clipToOutline"], requireAll = false)
    fun clipToOutline(view: View, clipToOutline: Boolean) {
        view.clipToOutline = clipToOutline
    }

    @JvmStatic
    @BindingAdapter(value = ["requestFocus"], requireAll = false)
    fun requestFocus(view: View, requestFocus: Boolean) {
        if (requestFocus) view.requestFocus()
    }

    @JvmStatic
    @BindingAdapter(value = ["showKeyboard"], requireAll = false)
    fun showKeyboard(view: View, showKeyboard: Boolean) {
        val imm =
            App.sApplication?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}