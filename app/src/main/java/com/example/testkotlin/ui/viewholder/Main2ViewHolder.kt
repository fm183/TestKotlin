package com.example.testkotlin.ui.viewholder

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testkotlin.R
import com.example.testkotlin.bean.ResponseBean
import com.example.testkotlin.core.AdapterManager
import com.example.testkotlin.inf.SelectedListener
import com.example.testkotlin.ui.adapter.Main2ChildAdapter

class Main2ViewHolder constructor(itemView: View) : BaseViewHolder(itemView),SelectedListener {

    private val recyclerView: RecyclerView = itemView.findViewById(R.id.rv_child)
    val tvName : TextView = itemView.findViewById(R.id.tv_name)
    private val ivIndicator: ImageView = itemView.findViewById(R.id.iv_indicator)
    private val rlSizeText : RelativeLayout = itemView.findViewById(R.id.rl_size_text)
    private val ivSelectStatus : ImageView = itemView.findViewById(R.id.iv_select_status)
    private val adapter = Main2ChildAdapter()
    private var isSelected : Boolean? = false
    private var isExpanded = false
    private var id : Int ?=0
    private var mChildrenBean:ResponseBean.DataBean.ChildrenBean? = null
    private var mDataBean:ResponseBean.DataBean ? = null

    init {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(itemView.context)
        AdapterManager.getInstance().addListener(this)
    }

    override fun setChildLevel(level: Int){
        super.setChildLevel(level)
        adapter.setChildLevel(level)
    }

    fun update(dataBean: ResponseBean.DataBean){
        tvName.text = dataBean.name

       updateData(dataBean,null)
    }

    fun update(childrenBean: ResponseBean.DataBean.ChildrenBean?){
        tvName.text = childrenBean?.name

        updateData(null,childrenBean)
    }

    private fun updateData(dataBean: ResponseBean.DataBean?,childrenBean: ResponseBean.DataBean.ChildrenBean?) {
        if(dataBean == null && childrenBean == null){
            return
        }
        val isChild = dataBean == null
        var childrenBeanList = dataBean?.children
        if(childrenBeanList == null){
            childrenBeanList = childrenBean?.children
        }
        mChildrenBean = childrenBean
        mDataBean = dataBean

        if (childrenBeanList == null) {
            recyclerView.visibility = View.GONE
            ivIndicator.visibility = View.INVISIBLE
        } else {
            ivIndicator.visibility = View.VISIBLE
        }

        ivIndicator.setOnClickListener {
            isExpanded = !isExpanded
            if(isSelected!! && childrenBeanList != null){
                updateChildrenBean(childrenBeanList)
            }
            updateAdapter(childrenBeanList)
        }

        isSelected = if(isChild) childrenBean?.isSelected else dataBean?.isSelected
        id = if(isChild) childrenBean?.id else dataBean?.id
        Log.d(javaClass.simpleName,"name="+tvName.text+",parentId="+id)
        rlSizeText.setOnClickListener {
            isSelected = !isSelected!!
            updateImageSelectedStatus()
            dataBean?.isSelected = isSelected as Boolean
            childrenBean?.isSelected = isSelected as Boolean
            if(childrenBeanList != null){
                if(recyclerView.visibility == View.VISIBLE && childrenBeanList.isNotEmpty()){
                    updateChildrenBean(childrenBeanList)
                    Log.d(javaClass.simpleName,"updateData childLevel="+getChildLevel()+",name="+tvName.text)
                    adapter.updateData(childrenBeanList)
                }
            }
            Log.d(javaClass.simpleName,"setOnClickListener name="+tvName.text+",adapterLevel="+getChildLevel())
            AdapterManager.getInstance().onChangeData(childrenBean,dataBean,getChildLevel())

        }
        updateImageSelectedStatus()
        updateAdapter(childrenBeanList)
    }

    private fun updateImageSelectedStatus(){
        ivSelectStatus.setImageResource(if (isSelected!!) R.mipmap.icon_xuanzhong else R.mipmap.icon_weixuanzhong)
    }

    private fun updateChildrenBean(childrenBeanList : List<ResponseBean.DataBean.ChildrenBean>){
        for (i in childrenBeanList.indices){
            val childrenBean2 = childrenBeanList[i]
            childrenBean2.isSelected = isSelected!!
            val childrenBeanList2 = childrenBean2.children
            if(childrenBeanList2 == null || childrenBeanList2.isEmpty()){
                continue
            }
            updateChildrenBean(childrenBeanList2)
        }
    }


    private fun updateAdapter(childrenBeanList: List<ResponseBean.DataBean.ChildrenBean>?) {
        recyclerView.visibility = if (isExpanded) View.VISIBLE else View.GONE
        if (isExpanded) {
            Log.d(javaClass.simpleName,"updateData childLevel="+getChildLevel()+",name="+tvName.text)
            adapter.updateData(childrenBeanList)
        }
    }

    override fun onSelectedChange(childrenBean: ResponseBean.DataBean.ChildrenBean?,dataBean: ResponseBean.DataBean?, level: Int) {
        var tParentId = dataBean?.parent_id
        if(tParentId == null){
            tParentId = childrenBean?.parent_id
        }
        Log.d(javaClass.simpleName,"setSelectedListener level="+level+",adapterLevel="+adapter.getChildLevel()+",tParentId="+tParentId
                +",id="+id +",name="+childrenBean?.name+",viewHolderName="+tvName.text)
        if((childrenBean == null && dataBean == null) || adapter.getChildLevel() != level - 1 || tParentId != id){
            return
        }
        var isSelected = childrenBean?.isSelected ?: dataBean?.isSelected
        adapter.updateSelectedCount(isSelected)

        Log.d(javaClass.simpleName,"setSelectedListener viewHolderName="+tvName.text+
                ",selectedCount="+adapter.getSelectedCount() + ",itemCount="+adapter.itemCount+",isSelected="+isSelected)
        if(adapter.getSelectedCount() == adapter.itemCount){
            isSelected = true
        }
        updateImageSelectedStatus()
        if (isSelected != null) {
            mChildrenBean?.isSelected = isSelected
            mDataBean?.isSelected = isSelected
        }
        AdapterManager.getInstance().onChangeData(mChildrenBean,mDataBean,getChildLevel())
    }

}