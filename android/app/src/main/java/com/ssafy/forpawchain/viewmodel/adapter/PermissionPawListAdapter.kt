package com.ssafy.forpawchain.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ControllMyPawListBinding
import com.ssafy.forpawchain.databinding.ControllPermissionPawListBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.PermissionUserDTO

class PermissionPawListAdapter(
    private var mydataSet: List<PermissionUserDTO>,
    val onClickDelButton: (pos: PermissionUserDTO) -> Unit,
) : RecyclerView.Adapter<PermissionPawListAdapter.PermissionPawListViewHolder>() {

    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    class PermissionPawListViewHolder(val binding: ControllPermissionPawListBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): PermissionPawListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.controll_permission_paw_list, viewGroup, false) //내가 각아이템에 사용하는 view
        val bind = ControllPermissionPawListBinding.bind(view)
        val holder = PermissionPawListViewHolder(bind)
        bind.deleteBtn.setOnClickListener {
            onClickDelButton.invoke(mydataSet.get(holder.adapterPosition))
        }
        return holder
    }


    override fun onBindViewHolder(
        todoViewHolder: PermissionPawListViewHolder,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            val listposition = mydataSet[position]
            todoViewHolder.binding.item = listposition
//
//            todoViewHolder.binding.qrBtn.setOnClickListener {
//                onClickQrButton.invoke(listposition) //눌렀을때 listposition를 전달하면서 함수를 실행한다.
//            }
        }
    }

    override fun getItemCount(): Int = mydataSet.size

    fun setData(data: List<PermissionUserDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}
