package com.ssafy.forpawchain.viewmodel.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.gson.JsonObject
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.databinding.ControllDiagnosisHistoryBinding
import com.ssafy.forpawchain.model.domain.HistoryDTO
import com.ssafy.forpawchain.model.service.UserService
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiagnosisRecyclerViewAdapter(
    val onClickDetailButton: (pos: HistoryDTO) -> Unit,
) :
    BaseRecyclerViewAdapter<ControllDiagnosisHistoryBinding, HistoryDTO>(emptyList()) {

    companion object {
        val TAG: String? = this::class.qualifiedName
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

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ControllDiagnosisHistoryBinding>,
        position: Int,
    ) {
        //item을 화면에 표시해주는
        val context : Context = holder.itemView.context
        val token : String = PreferenceManager().getString(context,"token")!!
        Log.d(TAG, "어댑터 token 잘 가져옴?" + token);

        if (mydataSet.isNotEmpty()) {
            var listposition = mydataSet[position]
            val historyDTO: HistoryDTO = mydataSet[position] as HistoryDTO

            CoroutineScope(Dispatchers.IO).launch {
                val response = withContext(Dispatchers.IO) {
                    UserService().getDoctorName(
                        historyDTO.writer,
                        token
                    ).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                        ) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성공된 경우
                                listposition.writer =
                                    response.body()?.get("content").toString().replace("\"", "")
                                holder.binding.item = listposition
                                // call
                                Log.d("DiagnosisRecyclerViewAdapter", "onResponse 성공");

                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d("DiagnosisRecyclerViewAdapter", "onResponse 실패")
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d("DiagnosisRecyclerViewAdapter", "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
                Log.d(TAG, "의사 이름 받아오기")
            }
        }
    }

    fun setData(data: List<HistoryDTO>) {
        mydataSet = data
        notifyDataSetChanged()
    }



}