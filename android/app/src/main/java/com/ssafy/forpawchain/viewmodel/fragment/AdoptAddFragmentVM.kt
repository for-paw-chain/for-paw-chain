package com.ssafy.forpawchain.viewmodel.fragment

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.view.accessibility.AccessibilityEventCompat.ContentChangeType
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class AdoptAddFragmentVM(application: Application) : AndroidViewModel(application) {
    val number = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val extra = MutableLiveData<String>()
    val path = MutableLiveData<String>()
    val token = PreferenceManager().getString(application,"token")!!

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    // 키와 값을 RequestBody로 변환하는 함수
    fun toRequestBody(key: String, value: String?): Pair<String, RequestBody?> {
        val requestBody = value?.toRequestBody("application/json".toMediaTypeOrNull())
        return Pair(key, requestBody)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun doneBtn_onClick() {
        // TODO: Done

        val file = path.value?.let { File(it) }
        val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = requestBody?.let {
            MultipartBody.Part.createFormData(
                "profile", file.name,
                it
            )
        }
        val payload = mapOf(
            "pid" to number.value,
            "etc" to extra.value,
            "tel" to phone.value
        )

        val jsonPayload = Gson().toJson(payload)
        val jsonRequestBody = jsonPayload.toRequestBody("application/json".toMediaTypeOrNull())
        val payloadPart = MultipartBody.Part.createFormData("content", null, jsonRequestBody)

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {
                if (imagePart != null) {
                    AdoptService().createAdopt(imagePart, payloadPart, token).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                        ) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성공된 경우
                                Log.d(TAG, "onResponse 성공");
                                _openEvent.value = Event(ActivityCode.DONE)
                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d(TAG, "onResponse 실패")
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(TAG, "onFailure 에러: " + t.message.toString())
                            _openEvent.value = Event(ActivityCode.DONE)
                        }
                    })
                }
            }
        }

//        _openEvent.value = Event(ActivityCode.DONE)

    }
}