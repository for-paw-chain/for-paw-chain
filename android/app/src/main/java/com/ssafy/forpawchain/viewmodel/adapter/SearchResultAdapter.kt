package com.ssafy.forpawchain.viewmodel.adapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllSearchResultListBinding
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class SearchResultAdapter(
    private var mydataSet: List<SearchResultDTO>,
    val onClickQrButton: (pos: SearchResultDTO) -> Unit,
    val onClickDetailButton: (pos: SearchResultDTO) ->Unit,
) : RecyclerView.Adapter<SearchResultAdapter.SearchRestulViewHolder>() {

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    class SearchRestulViewHolder(val binding: ControllSearchResultListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): SearchRestulViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_search_result_list, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllSearchResultListBinding.bind(view)
        val holder = SearchRestulViewHolder(bind)

        view.setOnClickListener({
            if(holder.adapterPosition != -1){
            onClickDetailButton.invoke(mydataSet.get(holder.adapterPosition))
            Log.d(TAG,"Search Result ${mydataSet.get(holder.adapterPosition)}")
            }
        })

        return holder
        //return SearchRestulViewHolder(ControllSearchResultListBinding.bind(view))
    }

    override fun onBindViewHolder(
        todoViewHolder: SearchResultAdapter.SearchRestulViewHolder,
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
