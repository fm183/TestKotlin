package com.example.testkotlin.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlin.R
import com.example.testkotlin.bean.ResponseBean
import com.example.testkotlin.ui.viewholder.Main2ViewHolder

class Main2ChildAdapter : RecyclerView.Adapter<Main2ViewHolder>() {

    private val list = ArrayList<ResponseBean.DataBean.ChildrenBean>()
    private var childLevel = 1
    private var selectedCount = 0

    fun updateData(list : List<ResponseBean.DataBean.ChildrenBean>?){
        this.list.clear()
        if(list != null){
            this.list.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun setChildLevel(childLevel : Int){
        this.childLevel = childLevel
    }

    fun getChildLevel(): Int {
        return childLevel
    }

    fun updateSelectedCount(isSelected: Boolean?){
        selectedCount += if(isSelected!!) 1 else -1
        if(selectedCount > itemCount){
            selectedCount = itemCount
        }else if(selectedCount < 0){
            selectedCount = 0
        }
    }

    fun getSelectedCount() :Int{
        return selectedCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Main2ViewHolder {
        return Main2ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item2,parent,false))
    }

    override fun onBindViewHolder(holder: Main2ViewHolder, position: Int) {
        holder.setChildLevel(this.childLevel + 1)
        holder.itemView.setPadding(30 * this.childLevel,30,0,30)
        val childrenBean = list[position]
        Log.d(javaClass.simpleName,"onBindViewHolder position="+position+",childrenName="+childrenBean.name+",childLevel="+childLevel)
        holder.tvName.text = childrenBean.name
        holder.update(childrenBean)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}