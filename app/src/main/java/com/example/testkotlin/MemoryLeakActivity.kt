package com.example.testkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class MemoryLeakActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_leak)
        Handler().postDelayed({
            Log.e(this@MemoryLeakActivity.javaClass.name,"onCreate postDelayed")
        },100000)
    }
}
