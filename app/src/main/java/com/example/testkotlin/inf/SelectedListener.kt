package com.example.testkotlin.inf

import com.example.testkotlin.bean.ResponseBean

interface SelectedListener {

    fun onSelectedChange(childrenBean: ResponseBean.DataBean.ChildrenBean?,dataBean: ResponseBean.DataBean?,level:Int)

}