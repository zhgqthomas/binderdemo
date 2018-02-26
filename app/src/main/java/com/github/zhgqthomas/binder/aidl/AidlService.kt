package com.github.zhgqthomas.binder.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

class AidlService : Service() {

    companion object {
        val TAG = "AidlService"
    }

    private val isServiceDestroyed = AtomicBoolean(false)
    private val bookLists = CopyOnWriteArrayList<Book>()
    private val remoteCallbacks = RemoteCallbackList<IBookManagerCallback>()

    private val binder = object : IBookManager.Stub() {

        override fun registerBookManagerCallback(callback: IBookManagerCallback?) {
            remoteCallbacks.register(callback)
        }

        override fun unregisterBookManagerCallback(callback: IBookManagerCallback?) {
            remoteCallbacks.unregister(callback)
        }

        override fun getBooks(): MutableList<Book> = bookLists

        override fun addBook(book: Book?) {
            bookLists.add(book)

            broadcastNewBookArrived(book)
        }
    }

    private fun broadcastNewBookArrived(book: Book?) {
        val n = remoteCallbacks.beginBroadcast()
        (0 until n)
                .map { remoteCallbacks.getBroadcastItem(it) }
                .forEach { callback ->
                    callback?.onNewBookArrived(book)
                }

        remoteCallbacks.finishBroadcast()
    }

    private val worker = Runnable {
        while (!isServiceDestroyed.get()) {
            Thread.sleep(1000)

            val bookId = bookLists.size + 1

            val book = Book(bookId, "new book: $bookId")
            bookLists.add(book)
            broadcastNewBookArrived(book)
        }
    }

    override fun onCreate() {
        super.onCreate()
        bookLists.add(Book(1, "Android"))
        bookLists.add(Book(2, "iOS"))

        Thread(worker).start()
    }

    override fun onDestroy() {
        isServiceDestroyed.set(true)
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = binder
}
