package com.example.testkotlin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlin.R
import com.example.testkotlin.bean.ResponseBean
import com.example.testkotlin.ui.adapter.Main2Adapter
import com.google.gson.Gson

class Main2Activity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)


        val json =
            "{\"code\":0,\"data\":[{\"id\":1,\"name\":\"组织架构\",\"parent_id\":0,\"create_time\":\"2020-08-28 15:55:00\",\"update_time\":null,\"sort\":1,\"leaders\":\"\",\"children\":[{\"id\":2,\"name\":\"五虎上将\",\"parent_id\":1,\"create_time\":\"2020-08-29 11:33:58\",\"update_time\":null,\"sort\":1,\"leaders\":\"\",\"children\":[{\"id\":8,\"name\":\"二级部门\",\"parent_id\":2,\"create_time\":\"2020-09-09 09:33:55\",\"update_time\":null,\"sort\":1,\"leaders\":\"\",\"children\":[{\"id\":9,\"name\":\"三级部门\",\"parent_id\":8,\"create_time\":\"2020-09-09 09:34:16\",\"update_time\":null,\"sort\":1,\"leaders\":\"\"}]}]},{\"id\":3,\"name\":\"三国之主\",\"parent_id\":1,\"create_time\":\"2020-08-29 11:47:01\",\"update_time\":null,\"sort\":1,\"leaders\":\"\"},{\"id\":5,\"name\":\"朕的后宫\",\"parent_id\":1,\"create_time\":\"2020-08-29 11:55:06\",\"update_time\":null,\"sort\":1,\"leaders\":\"\"},{\"id\":6,\"name\":\"五子良将\",\"parent_id\":1,\"create_time\":\"2020-08-31 11:09:27\",\"update_time\":null,\"sort\":1,\"leaders\":\"\"},{\"id\":7,\"name\":\"前端测试勿动\",\"parent_id\":1,\"create_time\":\"2020-09-07 20:11:05\",\"update_time\":null,\"sort\":1,\"leaders\":\"\"}]}],\"message\":\"成功\"}"
        val responseBean: ResponseBean = Gson().fromJson(json, ResponseBean::class.java)
        val adapter = Main2Adapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        if(responseBean.data == null){
            return
        }

        adapter.updateData(responseBean.data)
    }

}
