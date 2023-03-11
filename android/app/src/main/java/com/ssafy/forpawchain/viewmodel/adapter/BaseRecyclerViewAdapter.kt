package com.ssafy.forpawchain.viewmodel.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter<BIND : ViewDataBinding, DTO>(
    protected var mydataSet: List<DTO>,
) : RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseRecyclerViewHolder<BIND>>() {

    class BaseRecyclerViewHolder<BIND : ViewDataBinding>(var binding: BIND) :
        RecyclerView.ViewHolder(binding.root) {

    }

    abstract override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseRecyclerViewHolder<BIND>

    override fun getItemCount(): Int = mydataSet.size
}
