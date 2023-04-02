package com.ssafy.forpawchain.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControlDiagnosisAddFormBinding
import com.ssafy.forpawchain.databinding.ControlDiagnosisDetailBinding
import com.ssafy.forpawchain.model.domain.DianosisNewDTO

class DiagnosisDetailRecyclerViewAdapter :
    BaseRecyclerViewAdapter<ControlDiagnosisDetailBinding, DianosisNewDTO>(emptyList()) {

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControlDiagnosisDetailBinding>,
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
    ): BaseRecyclerViewHolder<ControlDiagnosisDetailBinding> {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.control_diagnosis_detail, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControlDiagnosisDetailBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)

        return holder
    }

    fun setData(data: List<DianosisNewDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}