package com.ssafy.forpawchain.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllMyPawListBinding
import com.ssafy.forpawchain.databinding.ControllSearchResultListBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class MyPawListAdapter(
    private var mydataSet: List<MyPawListDTO>,
    val onClickQrButton: (pos: MyPawListDTO) -> Unit
) : RecyclerView.Adapter<MyPawListAdapter.MyPawListViewHolder>() {

    class MyPawListViewHolder(val binding: ControllMyPawListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyPawListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_my_paw_list, viewGroup, false) //내가 각아이템에 사용하는 view

        return MyPawListViewHolder(ControllMyPawListBinding.bind(view))
    }


    override fun onBindViewHolder(
        todoViewHolder: MyPawListViewHolder,
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

    fun setData(data: List<MyPawListDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}
