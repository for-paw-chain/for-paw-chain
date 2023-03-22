package com.ssafy.forpawchain.viewmodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControlDiagnosisAddFormBinding
import com.ssafy.forpawchain.databinding.ControllDiagnosisHistoryBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.DiagnosisHistoryDTO
import com.ssafy.forpawchain.model.domain.DianosisNewDTO
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO

class DiagnosisNewRecyclerViewAdapter :
    BaseRecyclerViewAdapter<ControlDiagnosisAddFormBinding, DianosisNewDTO>(emptyList()) {

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControlDiagnosisAddFormBinding>,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            val listposition = mydataSet[position]
            holder.binding.item = listposition
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewHolder<ControlDiagnosisAddFormBinding> {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.control_diagnosis_add_form, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControlDiagnosisAddFormBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)

        return holder
    }

    fun setData(data: List<DianosisNewDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}