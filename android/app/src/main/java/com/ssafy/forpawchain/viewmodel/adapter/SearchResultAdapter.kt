package com.ssafy.forpawchain.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllSearchResultListBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.viewmodel.fragment.SearchResultFragmentVM

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

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SearchRestulViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_search_result_list, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllSearchResultListBinding.bind(view)
        val holder = SearchRestulViewHolder(bind)
//        view.setOnClickListener({
//            if(holder.adapterPosition != -1){
//                onClickDetailButton.invoke(mydataSet.get(holder.adapterPosition))
//                Log.d(TAG,"Search Result ${mydataSet.get(holder.adapterPosition)}")
//            }
//        })

        return holder
        //return SearchRestulViewHolder(ControllSearchResultListBinding.bind(view))
    }

    override fun onBindViewHolder(
        // holder: SearchRestulViewHolder,
        holder: SearchRestulViewHolder,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            val listposition = mydataSet[position]
            holder.binding.item = listposition
            holder.binding.qrBtn.setOnClickListener {
                onClickQrButton.invoke(listposition) //눌렀을때 listposition를 전달하면서 함수를 실행한다.
            }
//            holder.binding.idPawInfo.setOnClickListener{
//                Log.d(TAG,"여긴 어댑터 bindviewholder정보 ${mydataSet.get(holder.adapterPosition)}")
//                onClickDetailButton.invoke(listposition)
//            }
//            holder.binding.idPawImg.setOnClickListener{
//                Log.d(TAG,"여긴 어댑터 bindviewholder이미지 ${mydataSet.get(holder.adapterPosition)}")
//                onClickDetailButton.invoke(listposition)
//            }
        }
    }

    override fun getItemCount(): Int = mydataSet.size

    fun setData(data: List<SearchResultDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}
