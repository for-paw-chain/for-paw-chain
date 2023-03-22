package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.model.service.UserService
import com.ssafy.forpawchain.util.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyPawHistoryFragmentVM : ViewModel() {
    val selectedKind = MutableLiveData<Int>()
    val selectedNeutered = MutableLiveData<Int>()
    val selectedSex = MutableLiveData<Int>()

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    //추가 시작
    val todoLiveData = MutableLiveData<List<AdoptDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<AdoptDTO>()

    fun addTask(todo: AdoptDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: AdoptDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }

    suspend fun initData() {
        val response = withContext(Dispatchers.IO) {
            UserService().getPawHistory().enqueue(object :
                Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성고된 경우
                        var result: JsonObject? = response.body()
                        val items = result?.get("content") as JsonArray
                        for (item in items.asJsonArray) {
                            val item = item.asJsonObject
                            ImageLoader().loadDrawableFromUrl(item["profile"].asString) { drawable ->
                                // drawable 객체 사용
                                addTask(
                                    AdoptDTO(
                                        item["pid"].asString,
                                        drawable,
                                        item["type"].asString,
                                        item["kind"].asString,
                                        item["spayed"].asString
                                    )
                                )
                            }

                        }
                        Log.d(PawFragmentVM.TAG, "onResponse 성공: $result");
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d(PawFragmentVM.TAG, "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d(TAG, "onFailure 에러: " + t.message.toString());
                }
            })
        }
    }
}