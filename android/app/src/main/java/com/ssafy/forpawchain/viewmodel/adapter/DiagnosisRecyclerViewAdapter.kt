package com.ssafy.forpawchain.viewmodel.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.databinding.ControllDiagnosisHistoryBinding
import com.ssafy.forpawchain.model.domain.Data
import com.ssafy.forpawchain.model.domain.HistoryDTO
import com.ssafy.forpawchain.model.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.abi.datatypes.DynamicArray
import org.web3j.abi.datatypes.Utf8String
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger

class DiagnosisRecyclerViewAdapter(
    val onClickDetailButton: (pos: HistoryDTO) -> Unit,
) :
    BaseRecyclerViewAdapter<ControllDiagnosisHistoryBinding, HistoryDTO>(emptyList()) {

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControllDiagnosisHistoryBinding>,
        position: Int
    ) {
        //item을 화면에 표시해주는
        if (mydataSet.isNotEmpty()) {
            var listposition = mydataSet[position]

            val historyDTO: HistoryDTO = mydataSet[position] as HistoryDTO
            GlobalScope.launch {
                val response = withContext(Dispatchers.IO) {
                    UserService().getDoctorName(
                        historyDTO.writer
                    ).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                        ) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성공된 경우
                                listposition.writer = response.body()?.get("content").toString().replace("\"", "")
                                holder.binding.item = listposition
                                // call
                                Log.d(ForPawChain.TAG, "onResponse 성공");

                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d(ForPawChain.TAG, "onResponse 실패")
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(ForPawChain.TAG, "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
                Log.d(ForPawChain.TAG, "의사 이름 받아오기")
            }
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

    fun setData(data: List<HistoryDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }
}