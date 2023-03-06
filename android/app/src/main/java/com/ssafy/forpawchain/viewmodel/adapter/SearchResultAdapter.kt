package com.ssafy.forpawchain.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllSearchResultListBinding
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class SearchResultAdapter(
    private var mydataSet: List<SearchResultDTO>,
    val onClickQrButton: (pos: SearchResultDTO) -> Unit
) : RecyclerView.Adapter<SearchResultAdapter.SearchRestulViewHolder>() {

    class SearchRestulViewHolder(val binding: ControllSearchResultListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchRestulViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_search_result_list, viewGroup, false) //내가 각아이템에 사용하는 view

        return SearchRestulViewHolder(ControllSearchResultListBinding.bind(view))
    }


    override fun onBindViewHolder(
        todoViewHolder: SearchRestulViewHolder,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            val listposition = mydataSet[position]
            todoViewHolder.binding.item = listposition

            todoViewHolder.binding.qrBtn.setOnClickListener {
                onClickQrButton.invoke(listposition) //눌렀을때 listposition를 전달하면서 함수를 실행한다.
            }
        }
    }

    override fun getItemCount(): Int = mydataSet.size

    fun setData(data: List<SearchResultDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}
