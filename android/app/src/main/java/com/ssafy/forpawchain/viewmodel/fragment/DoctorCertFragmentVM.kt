package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import com.ssafy.forpawchain.model.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorCertFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent


    val name = MutableLiveData<String>()

    val socialSecurityNumberFront = MutableLiveData<String>()

    val socialSecurityNumberBack = MutableLiveData<String>()

    val phoneNumber = MutableLiveData<String>()

    val phoneCompany = MutableLiveData<Int>()

    fun summit_onClick() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {
                UserService().setDoctor(
                    RequestDoctorDTO(
                        name.value,
                        socialSecurityNumberFront.value + socialSecurityNumberBack.value,
                        phoneNumber.value,
                        phoneCompany.value?.plus(1)
                    )
                ).enqueue(object :
                    Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        if (response.isSuccessful) {
                            // 정상적으로 통신이 성공된 경우
                            var result: JsonObject? = response.body()

                            Log.d(TAG, "onResponse 성공: $result");
                            _openEvent.value = Event(ActivityCode.FRAGMENT_USER)

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

        Log.d(
            TAG,
            "${name.value} 님이 ${socialSecurityNumberFront.value}-${socialSecurityNumberBack.value} 주민번호에\n" +
                    "${phoneCompany.value} 통신사에 ${phoneNumber.value}로 가입"
        )
    }
}