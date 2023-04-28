package com.task.actionlist

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.NetworkUtils.NetworkType
import com.blankj.utilcode.util.NetworkUtils.OnNetworkStatusChangedListener
import com.blankj.utilcode.util.ToastUtils
import com.task.actionlist.databinding.ActivityMainBinding
import com.task.actionlist.domain.intent.Messages
import com.task.actionlist.domain.message.PageMessenger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val messenger by viewModels<PageMessenger>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ActivityMainBinding.inflate(layoutInflater)
        onOutput()
        App.isConnect = NetworkUtils.isConnected()
        setContentView(view.root)
        NetworkUtils.registerNetworkStatusChangedListener(object :
            OnNetworkStatusChangedListener {
            override fun onDisconnected() {
                ToastUtils.showShort(getString(R.string.disconnect))
                App.isConnect = false
            }

            override fun onConnected(networkType: NetworkType) {
                App.isConnect = networkType != NetworkType.NETWORK_NO
                Log.i("TAG", "onConnected: ")
                messenger.input(Messages.RefreshNoteList)
                ToastUtils.showShort(getString(R.string.connect))
            }
        })
        Log.i("TAG", "isConnect: ${App.isConnect}")

    }

    private fun onOutput() {
        messenger.output(this) { messages ->
            if (messages is Messages.FinishActivity) finish()
        }
    }

}