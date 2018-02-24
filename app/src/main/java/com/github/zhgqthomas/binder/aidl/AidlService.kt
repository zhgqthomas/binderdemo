package com.github.zhgqthomas.binder.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AidlService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }
}
