package com.app.mvvm_structure.utils

import androidx.annotation.LayoutRes

class RecyclerViewDataBindingAdapter<T: Any>: BaseRecycleAdapter {

    private var layout = 0
    private var listOfItem: ArrayList<T>? = null
    private var hashMap = HashMap<Int, Any>()

    constructor(@LayoutRes layout: Int, listOfItem: ArrayList<T>?) {
        this.layout = layout
        this.listOfItem = listOfItem
    }

    constructor(@LayoutRes layout: Int, listOfItem: ArrayList<T>?, hashMap: HashMap<Int, Any>) {
        this.layout = layout
        this.listOfItem = listOfItem
        this.hashMap = hashMap
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        for((key, value) in hashMap) {
            holder.binding.setVariable(key, value)
        }
        super.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return listOfItem!!.size
    }

    override fun getLayout(): Int {
        return layout
    }

    override fun getItemByPosition(position: Int): Any {
        return listOfItem!![position]
    }

}