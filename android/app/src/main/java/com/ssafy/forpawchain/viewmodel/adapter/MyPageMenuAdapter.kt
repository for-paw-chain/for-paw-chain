package com.ssafy.forpawchain.viewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllMypageMenuBinding
import com.ssafy.forpawchain.databinding.ControllSearchResultListBinding
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class MyPageMenuAdapter(
    private var mydataSet: List<MyPageMenuDTO>,
    val onClickQrButton: (pos: MyPageMenuDTO) -> Unit
) : RecyclerView.Adapter<MyPageMenuAdapter.MyPageMenuViewHolder>() {

    class MyPageMenuViewHolder(val binding: ControllMypageMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyPageMenuViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_mypage_menu, viewGroup, false) //내가 각아이템에 사용하는 view

        return MyPageMenuViewHolder(ControllMypageMenuBinding.bind(view))
    }


    override fun onBindViewHolder(
        todoViewHolder: MyPageMenuViewHolder,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            val listposition = mydataSet[position]
            todoViewHolder.binding.item = listposition

            todoViewHolder.binding.enterBtn.setOnClickListener {
                onClickQrButton.invoke(listposition) //눌렀을때 listposition를 전달하면서 함수를 실행한다.
            }
        }
    }

    override fun getItemCount(): Int = mydataSet.size

    fun setData(data: List<MyPageMenuDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}
