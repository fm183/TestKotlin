package com.example.testkotlin

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val serviceConnect = object: ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(MainActivity().javaClass.simpleName,"serviceConnect name="+name?.className)
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(MainActivity().javaClass.simpleName,"onServiceConnected name="+name?.className + ",service="+service?.javaClass)
            if(service is MyService.MyBinder){
                val number = service.getService().getRandomNumber()
                Log.d(MainActivity().javaClass.simpleName, "onServiceConnected number=$number")
            }
        }

    }

    private class MessengerHandler: Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(MainActivity().javaClass.simpleName,"handleMessage what="+msg.what)
            val bundle = msg.data
            val message = bundle.get("msg")
            Log.d(MainActivity().javaClass.simpleName, "handleMessage message=$message")
        }
    }

    private var messenger = Messenger(MessengerHandler())
    private var messengerService: Messenger?=null

    private val messageServiceConnection = object: ServiceConnection{
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(MainActivity().javaClass.simpleName,"messageServiceConnection serviceConnect name="+name?.className)
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(MainActivity().javaClass.simpleName,"messageServiceConnection onServiceConnected name="+name?.className + ",service="+service?.javaClass)
            messengerService = Messenger(service)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val data = ArrayList<String>()
        for (i in 0..20){
            data.add("item=$i")
        }
        val adapter = Adapter(data)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        adapter.setOnItemClickListener(object: Adapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(this@MainActivity, "item=$position",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@MainActivity,MemoryLeakActivity().javaClass))
            }

        })

        val canvasView = findViewById<ProgressView>(R.id.canvas_view)
        canvasView.setBaseColor(Color.BLACK)
        canvasView.setProgressColor(Color.GREEN)
        canvasView.setProgress(50)
        val intent = Intent(this@MainActivity,MyService().javaClass)
        intent.putExtra("from","MainActivity")
        bindService(intent,serviceConnect,BIND_AUTO_CREATE)

        val intentMessenger = Intent(this@MainActivity,MessageService().javaClass)
        bindService(intentMessenger,messageServiceConnection, BIND_AUTO_CREATE)

        canvasView.setOnClickListener {
            val message = Message.obtain()
            val bundle = Bundle()
            message.what = Constant.CLIENT_TYPE
            bundle.putString("msg","ss")
            message.data = bundle
            message.replyTo = messenger
            messengerService?.send(message)
        }
    }


    override fun onDestroy() {
        unbindService(serviceConnect)
        unbindService(messageServiceConnection)
        super.onDestroy()
    }

    class Adapter(private val data: List<String>) : RecyclerView.Adapter<ViewHolder>(){

        private var onItemClickListener:OnItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.tvTitle.text = data[position]
            holder.itemView.setOnClickListener{
                onItemClickListener?.onItemClick(holder.itemView,position)
            }
        }

        fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
            this.onItemClickListener = onItemClickListener
        }

        interface OnItemClickListener{
            fun onItemClick(view:View,position: Int)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    }
}
