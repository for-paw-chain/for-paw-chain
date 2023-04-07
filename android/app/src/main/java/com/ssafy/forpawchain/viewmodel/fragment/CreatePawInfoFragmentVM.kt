package com.ssafy.forpawchain.viewmodel.fragment

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.model.service.PetService
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreatePawInfoFragmentVM(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private lateinit var navController: NavController
    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent
    val code = MutableLiveData<String>()
    val path = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val region = MutableLiveData<String>()
    val year = MutableLiveData<String>()
    val month = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    //    val year = MutableLiveData<Int>()
//    val month = MutableLiveData<Int>()
//    val date = MutableLiveData<Int>()
    //    val birth = MutableLiveData<String>()
    val extra = MutableLiveData<String>()
    val pawInfo = MutableLiveData<SearchResultDTO>()

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun doneBtn_onClick(code: String) {

        val file = path.value?.let { File(it) }
        val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = requestBody?.let {
            MultipartBody.Part.createFormData(
                "profile", file.name,
                it
            )
        }

        try {
            // 예외가 발생할 가능성이 있는 코드
            val payload = mapOf(
                "pid" to code,
    //            "birth" to birth,
    //            "birth" to year.value.toString() + month.value.toString() + date.value.toString(),
                "birth" to year.value + "-" + month.value + "-" + date.value,
                "etc" to extra.value,
                "region" to region.value,
                "tel" to phone.value,
            )
        val jsonPayload = Gson().toJson(payload)
        val jsonRequestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        val payloadPart = MultipartBody.Part.createFormData("content", null, jsonRequestBody)

        if (imagePart != null) {
            PetService().createPawInfo(imagePart, payloadPart, UserInfo.token).enqueue(object :
                retrofit2.Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: Response<JsonObject>
                ) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        Log.d(TAG, "onResponse 성공");
                        navController.navigate(R.id.navigation_search_result)
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d(TAG, "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d(TAG, "onFailure 에러: " + t.message.toString())
                }
            })
        }  } catch (e: Exception) {
            // 예외 처리 코드
            Toast.makeText(context, "입력 값을 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
//        _openEvent.value = Event(ActivityCode.DONE)
    }

    fun imageSelect_onClick() {

    }

}