package com.ssafy.forpawchain.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllMyPawListBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO

class MyPawListAdapter(
    private var mydataSet: List<MyPawListDTO>,
    val onClickQrButton: (pos: MyPawListDTO) -> Unit,
    val onClickDelButton: (pos: MyPawListDTO) -> Unit,
    val onClickDetailButton: (pos: MyPawListDTO) -> Unit,
) : RecyclerView.Adapter<MyPawListAdapter.MyPawListViewHolder>() {

    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    class MyPawListViewHolder(val binding: ControllMyPawListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyPawListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_my_paw_list, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllMyPawListBinding.bind(view)
        val holder = MyPawListViewHolder(bind)
        view.setOnClickListener({
            if (holder.adapterPosition != -1) {

                onClickDetailButton.invoke(mydataSet.get(holder.adapterPosition))
                Log.d(TAG, "${mydataSet.get(holder.adapterPosition)} 나의 반려동물, 상세 페이지로 이동")
            }
        })

        view.setOnLongClickListener {
            if (holder.adapterPosition != -1) {
                onClickDelButton.invoke(mydataSet.get(holder.adapterPosition))
//            Log.d(TAG, "${mydataSet.get(holder.adapterPosition)} 나의 반려동물, 삭제 여부")
            }
            return@setOnLongClickListener true
        }

        return holder
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
