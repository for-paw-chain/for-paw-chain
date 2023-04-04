package com.ssafy.forpawchain.viewmodel.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.PermissionUserDTO
import com.ssafy.forpawchain.model.service.AuthService
import com.ssafy.forpawchain.util.ImageLoader
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PermissionPawFragmentVM(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext
    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    val name = MutableLiveData<String>()
    val code = MutableLiveData<String>()

    //추가 시작
    val todoLiveData = MutableLiveData<List<PermissionUserDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<PermissionUserDTO>()

    fun addTask(todo: PermissionUserDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: PermissionUserDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun deleteUserTask(todo: Int) {
        for (item in data) {
            if (item.code.substring(1).toString().equals(todo.toString())) {
                data.remove(item)
            }
        }
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }
    fun deleteAllTask() {
        for (item in data) {
            data.remove(item)
        }
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }

    suspend fun initData(pid: String) {
        val token = PreferenceManager().getString(context, "token")!!
        val response = withContext(Dispatchers.IO) {
            AuthService().getAuth(pid, token).enqueue(object :
                Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성고된 경우
                        var result: JsonObject? = response.body()
                        val items = result?.get("content") as JsonArray
                        if (result != null) {
                            for (item in items.asJsonArray) {
                                var item1 = item as JsonObject
                                if (item1["profile"].toString() == "null") {
                                    addTask(
                                        PermissionUserDTO(
                                            null,
                                            item1["name"].asString,
                                            "#" + item["uid"].asString
                                        )
                                    )
                                } else {
                                    ImageLoader().loadDrawableFromUrl(item1["profile"].asString) { drawable ->
                                        addTask(
                                            PermissionUserDTO(
                                                drawable,
                                                item1["name"].asString,
                                                "#" + item["uid"].asString
                                            )
                                        )
                                    }
                                }

                            }
                        }
                        Log.d(TAG, "onResponse 성공: $result");
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d(TAG, "onResponse 실패")
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