package com.app.mvvm_structure.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecycleAdapter: RecyclerView.Adapter<BaseRecycleAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), getLayout(), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val obj = getItemByPosition(position)
        holder.bind(obj)
    }

    abstract override fun getItemCount(): Int

    abstract fun getLayout(): Int

    abstract fun getItemByPosition(position: Int): Any

    class MyViewHolder(var binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: Any?) {
            /*binding.setVariable(BR.item, obj)
            binding.setVariable(BR.position, adapterPosition)
            binding.executePendingBindings()*/
        }
    }

}