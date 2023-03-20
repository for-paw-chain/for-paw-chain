package com.ssafy.forpawchain.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllAdoptAdBinding
import com.ssafy.forpawchain.databinding.ControllMypageMenuBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO

class AdoptRecyclerViewAdapter(
    val onClickDetailButton: (pos: AdoptDTO) -> Unit,
    val onClickCRUEButton: (pos: AdoptDTO) -> Unit
) :
    BaseRecyclerViewAdapter<ControllAdoptAdBinding, AdoptDTO>(emptyList()) {

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControllAdoptAdBinding>,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            val listposition = mydataSet[position]
            holder.binding.item = listposition
            holder.binding.detailBtn.setOnClickListener() {
                if (holder.adapterPosition != -1) {
                    onClickDetailButton.invoke(mydataSet.get(holder.adapterPosition))
                }
            }

        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<ControllAdoptAdBinding> {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_adopt_ad, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllAdoptAdBinding.bind(view)

        val holder = BaseRecyclerViewHolder(bind)

        view.setOnLongClickListener {
            if (holder.adapterPosition != -1) {
                onClickCRUEButton.invoke(mydataSet.get(holder.adapterPosition))
            }
            return@setOnLongClickListener true
        }
        return holder
    }

    fun setData(data: List<AdoptDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}