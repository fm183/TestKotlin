package com.example.testkotlin.bean

class ResponseBean {
    var data: List<DataBean>? = null

    class DataBean {
        var id = 0
        var name: String? = null
        var parent_id = 0
        var create_time: String? = null
        var update_time: String? = null
        var sort = 0
        var leaders: String? = null
        var children: List<ChildrenBean>? = null
        var isSelected = false

        fun selectedAll(selected: Boolean){

        }

        class ChildrenBean {

            var id = 0
            var name: String? = null
            var parent_id = 0
            var create_time: String? = null
            var update_time: Any? = null
            var sort = 0
            var leaders: String? = null
            var children: List<ChildrenBean>? = null
            var isSelected = false

            fun selectedAll(selected: Boolean){

            }
        }
    }
}