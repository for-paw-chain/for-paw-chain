package com.ssafy.forpawchain.viewmodel.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.kakao.sdk.auth.model.OAuthToken
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.service.TestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginVM : ViewModel() {

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    companion object {
        val TAG: String? = this::class.qualifiedName
        val testService: TestService = TestService()
    }

    val id = MutableLiveData<String>()
//    val id: LiveData<String> get() = _id

    val pw = MutableLiveData<String>()
//    val pw: LiveData<String> get() = _pw


//    fun kakaoLogin_onClick() {
////        testService.test("ddd").enqueue(object : Callback<JsonObject> {
////            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
////                var result: JsonObject? = response.body()
////                if (response.isSuccessful) {
////                    // 정상적으로 통신이 성공된 경우
////                    Log.d(TAG, "onResponse 성공: " + result?.toString());
////
////
////                } else {
////                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
////                    Log.d(TAG, "onResponse 실패" + result?.toString())
////                }
////            }
////
////            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
////                // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
////                Log.d(TAG, "onFailure 에러: " + t.message.toString());
////            }
////        })
////        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
//    }

    fun googleLogin_onClick() {
        Log.d(TAG, "구글 로그인")
        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
    }

    fun naverLogin_onClick() {
        Log.d(TAG, "네이버 로그인")
        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
    }

    fun generalLogin_onClick() {
        Log.d(TAG, "일반 로그인")
        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
    }

}