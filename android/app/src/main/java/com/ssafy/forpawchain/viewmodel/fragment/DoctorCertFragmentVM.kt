package com.ssafy.forpawchain.viewmodel.fragment

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.model.domain.RequestDoctorDTO
import com.ssafy.forpawchain.model.domain.User
import com.ssafy.forpawchain.model.room.AppDatabase
import com.ssafy.forpawchain.model.service.UserService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DoctorCertFragmentVM(val application: Context) : ViewModel() {
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

    @OptIn(DelicateCoroutinesApi::class)
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
                            val privateKey: String = result?.get("content")?.asString ?: ""
                            CoroutineScope(Dispatchers.IO).launch {
                                val db = Room.databaseBuilder(
                                    application.applicationContext,
                                    AppDatabase::class.java, "database-name"
                                ).build()
                                val userDao = db.userDao()
                                val user = userDao.getUserById("private")
                                //의사 인증이 되면 private 키가 날라오는데 이것을 Room에 저장해야함
                                if (user == null) {
                                    userDao.insert(User("private", privateKey))
                                } else {
                                    user.privateKey = privateKey
                                    userDao.updateUser(user)
                                    ForPawChain.setWallet(privateKey)
                                }
                                Log.d(TAG, "onResponse 성공: $result");
                            }
                            _openEvent.value = Event(ActivityCode.FRAGMENT_USER)
                        } else {
                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                            Log.d(TAG, "onResponse 실패 " + response.errorBody()?.string()!!)
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