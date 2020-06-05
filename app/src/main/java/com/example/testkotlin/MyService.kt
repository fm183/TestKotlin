package com.example.testkotlin

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import java.util.*

class MyService : Service() {

    private val binder = MyBinder()
    private val random = Random()

    class MyBinder : Binder(){
        fun getService(): MyService {
            return MyService()
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(javaClass.simpleName,"onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(
            javaClass.simpleName,
            "TestTwoService - onStartCommand - startId = $startId, Thread = " + Thread.currentThread()
                .name
        )
        return START_NOT_STICKY
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        Log.d(
            javaClass.simpleName,
            "TestTwoService - onBind - Thread = " + Thread.currentThread().name
        )
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(
            javaClass.simpleName,
            "TestTwoService - onUnbind - from = " + intent.getStringExtra("from")
        )
        return false
    }

    override fun onDestroy() {
        Log.d(
            javaClass.simpleName,
            "TestTwoService - onDestroy - Thread = " + Thread.currentThread().name
        )
        super.onDestroy()
    }

    //getRandomNumber是Service暴露出去供client调用的公共方法
    fun getRandomNumber(): Int {
        return random.nextInt(10)
    }
}
