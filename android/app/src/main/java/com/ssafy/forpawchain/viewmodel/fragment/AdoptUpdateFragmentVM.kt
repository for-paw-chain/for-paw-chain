package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.blockchain.Forpawchain_sol_Storage
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread


class AdoptUpdateFragmentVM : ViewModel() {
    val number = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val extra = MutableLiveData<String>()

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun doneBtn_onClick() {
        // TODO: Done
        _openEvent.value = Event(ActivityCode.DONE)

    }

    fun imageSelected_onClick() {
        // TODO: Done
        Log.d(TAG, "이미지 선택")
    }
}