package com.example.testkotlin.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlin.R
import com.example.testkotlin.bean.ResponseBean
import com.example.testkotlin.ui.viewholder.Main2ViewHolder

class Main2Adapter : RecyclerView.Adapter<Main2ViewHolder>() {

    private val list = ArrayList<ResponseBean.DataBean>()

    fun updateData(list : List<ResponseBean.DataBean>?){
        this.list.clear()
        if(list != null){
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Main2ViewHolder {
        return Main2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item2,parent,false))
    }

    override fun onBindViewHolder(holder: Main2ViewHolder, position: Int) {
        holder.setChildLevel(1)
        holder.update(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}