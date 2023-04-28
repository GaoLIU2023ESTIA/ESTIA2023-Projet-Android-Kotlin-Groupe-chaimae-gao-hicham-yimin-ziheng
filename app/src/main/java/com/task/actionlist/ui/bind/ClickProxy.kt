package com.task.actionlist.ui.bind

import android.view.View
import androidx.databinding.BindingAdapter
import android.widget.TextView

class ClickProxy : View.OnClickListener {

    var listener: View.OnClickListener? = null

    fun setOnClickListener(listener: View.OnClickListener?) {
        this.listener = listener
    }

    override fun onClick(view: View) {
        listener!!.onClick(view)
    }
}
