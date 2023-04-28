package com.task.actionlist.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.kunminx.architecture.ui.page.DataBindingFragment
import com.task.actionlist.R


abstract class BaseFragment : DataBindingFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitData()
        onOutput()
        onInput()
    }

    override fun initViewModel() {
    }

    protected open fun onInitData() {}
    protected open fun onOutput() {}
    protected open fun onInput() {}


    protected fun nav(): NavController {
        return NavHostFragment.findNavController(this)
    }

    private var toast: Toast? = null
    fun shortToast(string: String) {
        toast?.cancel()
        toast = Toast.makeText(requireActivity(), string, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun longToast(string: String) = Toast.makeText(requireActivity(), string, Toast.LENGTH_LONG).show()


}