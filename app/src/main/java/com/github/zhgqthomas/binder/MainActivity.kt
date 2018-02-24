package com.github.zhgqthomas.binder

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.github.zhgqthomas.binder.aidl.AidlActivity
import com.github.zhgqthomas.binder.messenger.MessengerActivity
import com.github.zhgqthomas.binder.pool.BinderPoolActivity

class MainActivity : AppCompatActivity() {

    private lateinit var messengerBtn: Button
    private lateinit var aidlBtn: Button
    private lateinit var binderPoolBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messengerBtn = findViewById(R.id.btn_messenger)
        messengerBtn.setOnClickListener {
            startActivity(Intent(this, MessengerActivity::class.java))
        }

        aidlBtn = findViewById(R.id.btn_aidl)
        aidlBtn.setOnClickListener {
            startActivity(Intent(this, AidlActivity::class.java))
        }

        binderPoolBtn = findViewById(R.id.btn_binder_pool)
        binderPoolBtn.setOnClickListener {
            startActivity(Intent(this, BinderPoolActivity::class.java))
        }
    }
}
