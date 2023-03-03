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


class PawFragmentVM : ViewModel() {
    val numberValue = MutableLiveData<String>("0")
    val statusText = MutableLiveData<String>("읽은 값")


    companion object {
//        val web3 = Web3j.build(HttpService("http://183.97.128.216:7545/")) // defaults to http://localhost:8545/

        // 2849717dc6944af6a40ccf1540bdcb91
        val web3 =
            Web3j.build(InfuraHttpService("https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91"))

        //        val web3 = Web3jFactory.build()
        val web3ClientVersion = web3.web3ClientVersion().sendAsync().get()

        // contract address
        val contractAddress = "0x8442d3ef77b8fEEE80A422C951c0BDEb600bb748"

        // gas limit
        val gasLimit: BigInteger = BigInteger.valueOf(3000000)

        // gas price
        val gasPrice: BigInteger = BigInteger.valueOf(3000)

        // create credentials w/ your private key
        val credentials =
            Credentials.create("cbbfa9f3fac6ffcbd8ab8431e645c737c3a796b1eebce200499c180615e39787")
    }

    fun writeBtn_onClick() {
        Log.d(
            LoginVM.TAG,
            "MainFragment - MainFragmentVM - 버튼 클릭"
        )

        val contract =
            Contract_sol_Storage.load(contractAddress, web3, credentials, gasPrice, gasLimit)

        thread {
            val data =
                contract.store(numberValue.value?.let { BigInteger.valueOf(it.toLong()) })
                    .sendAsync()
            Log.d(LoginVM.TAG, "send result ${data.get().blockNumber}, ${data.get().gasUsed}")
            numberValue.postValue("0")
        }
    }

    fun readBtn_onClick() {
        Log.d(
            LoginVM.TAG,
            "MainFragment - MainFragmentVM - 버튼 클릭"
        )

        val contract =
            Contract_sol_Storage.load(contractAddress, web3, credentials, gasPrice, gasLimit)

        thread {
            // 값 읽기는 어떻게 읽는거냐-
            val temp = contract.retrieve().sendAsync()
            val value = temp.get()
            statusText.postValue(value.toString())
            Log.d(LoginVM.TAG, "recv result ${value}")
        }
    }
}