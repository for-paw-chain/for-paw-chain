package com.ssafy.forpawchain.viewmodel.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event

class LoginVM : ViewModel() {
    /*
        MutableLiveData = get; set
               LiveData = get;
     */

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    companion object {
        const val TAG: String = "LoginVM"
    }

    val id = MutableLiveData<String>()
//    val id: LiveData<String> get() = _id

    val pw = MutableLiveData<String>()
//    val pw: LiveData<String> get() = _pw


    fun kakaoLogin_onClick() {
        Log.d(TAG, "카카오 로그인")
        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
    }

    fun googleLogin_onClick() {
        Log.d(TAG, "구글 로그인")
        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
    }

    fun naverLogin_onClick() {
        Log.d(TAG, "네이버 로그인")
        _openEvent.value = Event(ActivityCode.MAIN_ACTIVITY)
    }

}