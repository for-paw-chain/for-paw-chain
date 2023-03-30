package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.model.domain.Data
import com.ssafy.forpawchain.model.domain.DianosisNewDTO
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.model.service.IpfsService
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class CreateDoctorHistoryFragmentVM : ViewModel() {
    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent
    val number = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val path = MutableLiveData<String>()

    //추가 시작
    val todoLiveData = MutableLiveData<List<DianosisNewDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<DianosisNewDTO>()


    fun addTask(todo: DianosisNewDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: DianosisNewDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun doneBtn_onClick() {
        var list: ArrayList<Data> = ArrayList()
        for (item in data) {
            list.add(Data(item.title.value, item.body.value))
            Log.d(TAG, "item: ${item}")
        }
        val file = path.value?.let { File(it) }
        val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = requestBody?.let {
            MultipartBody.Part.createFormData(
                "file", file.name,
                it
            )
        }
        if (imagePart != null) {
            IpfsService().uploadImage(imagePart).enqueue(object :
                retrofit2.Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        val data = response.body()
                        Log.d(AdoptAddFragmentVM.TAG, "onResponse 성공");
                        title.value?.let {
                            body.value?.let { it1 ->
                                ForPawChain.createHistory(
                                    it,
                                    it1, list, data!!["content"].toString().replace("\"", "")
                                )
                            }
                        }
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d(AdoptAddFragmentVM.TAG, "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d(AdoptAddFragmentVM.TAG, "onFailure 에러: " + t.message.toString())
                }
            })
        }

        clearTask()
        _openEvent.value = Event(ActivityCode.DONE)
    }


    fun addForm_onClick() {
        addTask(DianosisNewDTO(MutableLiveData<String>(""), MutableLiveData<String>("")))
    }

    fun imageSelect_onClick() {

    }

}