package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.DiagnosisHistoryDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdoptViewFragmentVM : ViewModel() {
    val extra = MutableLiveData<String>()

    var pawInfo: MyPawListDTO = MyPawListDTO(
        MutableLiveData(""),
        MutableLiveData(null),
        MutableLiveData(""),
        MutableLiveData(""),
        MutableLiveData(""),
        MutableLiveData(""),
        MutableLiveData("")
    )

    //추가 시작
    val todoLiveData = MutableLiveData<List<DiagnosisHistoryDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<DiagnosisHistoryDTO>()

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun addTask(todo: DiagnosisHistoryDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: DiagnosisHistoryDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        pawInfo.profile?.value = null
        pawInfo.name.value = ""
        pawInfo.sex.value = ""
        pawInfo.species.value = ""
        pawInfo.kind.value = ""
        pawInfo.neutered.value = ""
        extra.value = ""

        data.clear()
        todoLiveData.value = data
    }

    suspend fun initInfo(pid: String) {
        val response = withContext(Dispatchers.IO) {
            AdoptService().getDetailAdopt(pid).enqueue(object :
                Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성고된 경우
                        var result: JsonObject? = response.body()
                        ImageLoader().loadDrawableFromUrl(result!!["profile"].asString) { drawable ->
                            pawInfo.profile?.postValue(drawable)
                            pawInfo.name.postValue(result["name"].asString)
                            pawInfo.sex.postValue(result["sex"].asString)
                            pawInfo.species.postValue(result["type"].asString)
                            pawInfo.kind.postValue(result["kind"].asString)
                            pawInfo.neutered.postValue(result["spayed"].asString)
                            extra.postValue(result["etc"].asString)
                        }

                        Log.d(TAG, "onResponse 성공: $result");
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d(TAG, "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d(PawFragmentVM.TAG, "onFailure 에러: " + t.message.toString());
                }
            })
        }
    }
}