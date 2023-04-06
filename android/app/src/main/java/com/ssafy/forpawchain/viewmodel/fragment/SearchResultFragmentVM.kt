package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.domain.HistoryDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultFragmentVM : ViewModel() {
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
    val todoLiveData = MutableLiveData<List<HistoryDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<HistoryDTO>()

    fun addTask(todo: HistoryDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: HistoryDTO) {
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
                            pawInfo.sex.postValue(if (result["sex"].asString.equals("MALE")) {"남아"} else {"여아"})
                            pawInfo.species.postValue(if (result["type"].asString.equals("DOG")) "개과" else if (result["type"].asString.equals("CAT")) "고양이과" else "기타")
                            pawInfo.kind.postValue(result["kind"].asString)
                            pawInfo.neutered.postValue(if (result["spayed"].asString.equals("false")) "Ⅹ" else if(result["spayed"].asString.equals("true")) "○" else "")
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
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    val selectedSearchResult = MutableLiveData<SearchResultDTO>() // 변경, 관찰 가능한 List

}