package com.github.zhgqthomas.binder.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.github.zhgqthomas.binder.R

class AidlActivity : AppCompatActivity() {

    companion object {
        val TAG = "AidlActivity"
    }

    private val bookManagerCallback = object : IBookManagerCallback.Stub() {

        override fun onNewBookArrived(book: Book?) {
            Log.d(TAG, "new book arrived: $book")
        }
    }

    private lateinit var bookManager : IBookManager

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            bookManager = IBookManager.Stub.asInterface(service)
            bookManager.registerBookManagerCallback(bookManagerCallback)
            try {
                val list = bookManager.books
                Log.d(TAG, "query book list, list type: ${list::class.java.canonicalName}")
                Log.d(TAG, "query book list: $list")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aidl)
        bindService(Intent(this, AidlService::class.java), connection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {

        if (bookManager.asBinder().isBinderAlive) {
            bookManager.unregisterBookManagerCallback(bookManagerCallback)
        }

        unbindService(connection)
        super.onDestroy()
    }
}
