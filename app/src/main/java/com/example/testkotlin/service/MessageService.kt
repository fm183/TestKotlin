package com.example.testkotlin.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import com.example.testkotlin.config.Constant

class MessageService : Service() {

    class MessageHandler : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(MessageService().javaClass.simpleName,"handleMessage what="+msg.what)
            val receiveBundle = msg.data
            Log.d(MessageService().javaClass.simpleName,"handleMessage msg="+receiveBundle.getString("msg"))
            if(msg.what == Constant.CLIENT_TYPE){
                val client = msg.replyTo
                val message = Message.obtain()
                message.what = Constant.SERVICE_TYPE
                val bundle = Bundle()
                bundle.putString("msg","I am service message")
                message.data = bundle
                client?.send(message)
            }
        }
    }

    private val message = Messenger(MessageHandler())

    override fun onBind(intent: Intent): IBinder {
        return message.binder
    }



}
