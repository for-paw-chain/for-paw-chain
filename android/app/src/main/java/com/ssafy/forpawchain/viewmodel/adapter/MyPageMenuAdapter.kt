package com.ssafy.forpawchain.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllMyPawListBinding
import com.ssafy.forpawchain.databinding.ControllMypageMenuBinding
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO

class MyPageMenuAdapter(
    val onClickEnterButton: (pos: MyPageMenuDTO) -> Unit
) : BaseRecyclerViewAdapter<ControllMypageMenuBinding, MyPageMenuDTO>(emptyList()) {


    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControllMypageMenuBinding>,
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
    ): BaseRecyclerViewHolder<ControllMypageMenuBinding> {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_mypage_menu, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllMypageMenuBinding.bind(view)

        val holder = BaseRecyclerViewHolder(bind)
        view.setOnClickListener {
            if (holder.adapterPosition != -1) {
                onClickEnterButton.invoke(mydataSet.get(holder.adapterPosition))
                Log.d(
                    MyPawListAdapter.TAG,
                    "${mydataSet.get(holder.adapterPosition)} 나의 반려동물, 상세 페이지로 이동"
                )
            }
        }
        return holder
    }

    fun setData(data: List<MyPageMenuDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}
