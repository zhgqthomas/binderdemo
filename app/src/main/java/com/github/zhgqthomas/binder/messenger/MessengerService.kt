package com.github.zhgqthomas.binder.messenger

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.github.zhgqthomas.binder.Constants

class MessengerService : Service() {

    companion object {
        val TAG  = "MessengerService"
    }

    private class MessengerHandler : Handler() {

        override fun handleMessage(msg: Message?) {

            when (msg!!.what) {
                Constants.MSG_FROM_CLIENT -> {
                    Log.d(TAG, "receive msg from client: ${msg.data.getString("msg")}")

                    val message = Message.obtain(null, Constants.MSG_FROM_SERVER)
                    val client = msg.replyTo
                    val data = Bundle()
                    data.putString("msg", "I got your msg, this is server")
                    message.data = data

                    try {
                        client.send(message)
                    } catch (e: RemoteException) {
                        e.printStackTrace()
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    private val messenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent): IBinder? {
       return messenger.binder
    }
}
