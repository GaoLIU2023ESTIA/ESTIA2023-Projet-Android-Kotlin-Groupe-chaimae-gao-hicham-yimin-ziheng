package com.task.actionlist

import android.annotation.SuppressLint
import android.app.Application
import com.blankj.utilcode.util.NetworkUtils
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        var sApplication: Application? = null
        var isConnect = true
    }

    override fun onCreate() {
        super.onCreate()
        sApplication = this
        MMKV.initialize(this)
    }
}