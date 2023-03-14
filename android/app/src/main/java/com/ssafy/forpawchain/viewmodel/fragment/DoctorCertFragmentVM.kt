package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.viewmodel.activity.LoginVM
import com.ssafy.forpawchain.blockchain.Contract_sol_Storage
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread


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
        _openEvent.value = Event(ActivityCode.FRAGMENT_USER)
    }
}