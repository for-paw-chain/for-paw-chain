package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.forpawchain.viewmodel.activity.LoginVM
import com.ssafy.forpawchain.blockchain.Contract_sol_Storage
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread


class UserFragmentVM : ViewModel() {
    val message = MutableLiveData<String>("유저 프래그먼트입니다!")
}