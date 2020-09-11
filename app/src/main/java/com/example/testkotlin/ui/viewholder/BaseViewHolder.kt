package com.example.testkotlin.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder(itemView : View ) : RecyclerView.ViewHolder(itemView) {

    private var childLevel = 1

   open fun setChildLevel(level: Int){
        childLevel = level
    }

    fun getChildLevel(): Int {
        return childLevel
    }
}