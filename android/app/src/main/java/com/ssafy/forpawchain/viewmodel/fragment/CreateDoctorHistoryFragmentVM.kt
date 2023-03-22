package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.blockchain.Forpawchain_sol_Storage
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.service.IpfsService
import com.ssafy.forpawchain.util.ImageLoader
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.math.BigInteger
import kotlin.concurrent.thread


class CreateDoctorHistoryFragmentVM : ViewModel() {
    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent
    val number = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val path = MutableLiveData<String>()


    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun doneBtn_onClick() {
        val imageFile = path.value?.let { File(it) }

        // RequestBody 생성
        val requestBody = imageFile?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }

        // MultipartBody.Part 생성
        val filePart =
            requestBody?.let { MultipartBody.Part.createFormData("file", imageFile.name, it) }

        if (filePart != null) {
            IpfsService().uploadImage(filePart).enqueue(object :
                retrofit2.Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        // 정상적으로 통신이 성공된 경우
                        var result: JsonObject? = response.body()

                        _openEvent.value = Event(ActivityCode.DONE)

                        Log.d(PawFragmentVM.TAG, "onResponse 성공: $result");
                    } else {
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d(PawFragmentVM.TAG, "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d(PawFragmentVM.TAG, "onFailure 에러: " + t.message.toString());
                }
            })

        }
    }

    fun imageSelect_onClick() {

    }
}