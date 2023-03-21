package com.ssafy.forpawchain.viewmodel.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllDiagnosisHistoryBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.DiagnosisHistoryDTO
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO

class DiagnosisRecyclerViewAdapter(
    val onClickDetailButton: (pos: DiagnosisHistoryDTO) -> Unit,
) :
    BaseRecyclerViewAdapter<ControllDiagnosisHistoryBinding, DiagnosisHistoryDTO>(emptyList()) {

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControllDiagnosisHistoryBinding>,
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
    ): BaseRecyclerViewHolder<ControllDiagnosisHistoryBinding> {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_diagnosis_history, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllDiagnosisHistoryBinding.bind(view)
        val holder = BaseRecyclerViewHolder(bind)

        view.setOnClickListener {
            if (holder.adapterPosition != -1) {
                onClickDetailButton.invoke(mydataSet.get(holder.adapterPosition))
            }
        }
        return holder
    }

    fun setData(data: List<DiagnosisHistoryDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}