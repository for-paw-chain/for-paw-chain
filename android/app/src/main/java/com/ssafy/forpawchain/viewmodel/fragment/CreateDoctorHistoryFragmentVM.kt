package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.domain.DianosisNewDTO


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
//        val imageFile = path.value?.let { File(it) }
//
//        // RequestBody 생성
//        val requestBody = imageFile?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }
//
//        // MultipartBody.Part 생성
//        val filePart =
//            requestBody?.let { MultipartBody.Part.createFormData("file", imageFile.name, it) }
//
//        if (filePart != null) {
//            IpfsService().uploadImage(filePart).enqueue(object :
//                retrofit2.Callback<JsonObject> {
//                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
//                    if (response.isSuccessful) {
//                        // 정상적으로 통신이 성공된 경우
//                        var result: JsonObject? = response.body()
//
//                        _openEvent.value = Event(ActivityCode.DONE)
//
//                        Log.d(PawFragmentVM.TAG, "onResponse 성공: $result");
//                    } else {
//                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
//                        Log.d(PawFragmentVM.TAG, "onResponse 실패")
//                    }
//                }
//
//                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
//                    Log.d(PawFragmentVM.TAG, "onFailure 에러: " + t.message.toString());
//                }
//            })
//
//        }
        for (item in data) {
            Log.d(TAG, "item: ${item}")
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