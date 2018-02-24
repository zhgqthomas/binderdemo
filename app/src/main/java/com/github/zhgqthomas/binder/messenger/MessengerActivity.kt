package com.github.zhgqthomas.binder.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.zhgqthomas.binder.Constants
import com.github.zhgqthomas.binder.R

class MessengerActivity : AppCompatActivity() {

    companion object {
        val TAG = "MessengerActivity"
    }

    private class ClientMessengerHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when(msg!!.what) {
                Constants.MSG_FROM_SERVER -> {
                    Log.d(TAG, "receive msg from server: ${msg.data.getString("msg")}")
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private val messenger = Messenger(ClientMessengerHandler())

    private lateinit var remote: Messenger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        bindService(Intent(this, MessengerService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(connection)
        super.onDestroy()
    }

    private val connection = object: ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            remote = Messenger(service)
            val msg = Message.obtain(null, Constants.MSG_FROM_CLIENT)
            val data = Bundle()
            data.putString("msg", "hello, this is client.")
            msg.data = data
            msg.replyTo = messenger

            try {
                remote.send(msg)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }
}
