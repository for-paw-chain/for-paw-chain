package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.forpawchain.blockchain.Forpawchain_sol_Storage
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
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
        val TAG: String? = this::class.qualifiedName


        // https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91
        val web3 =
            Web3j.build(InfuraHttpService("http://3.39.235.238:8545"))

        //        val web3 = Web3jFactory.build()
        val web3ClientVersion = web3.web3ClientVersion().sendAsync().get()

        // contract address
        val contractAddress = "0x2396007A802a0381891fD1308C4b7e3c2655FdB5"

        // gas limit
        val gasLimit: BigInteger = BigInteger.valueOf(3000000)

        // gas price
        val gasPrice: BigInteger = BigInteger.valueOf(3000)

        // create credentials w/ your private key
        val credentials =
            Credentials.create("6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544")
    }

    fun writeBtn_onClick() {
        Log.d(
            TAG,
            "MainFragment - MainFragmentVM - 버튼 클릭"
        )

        val contract =
            Forpawchain_sol_Storage.load(contractAddress, web3, credentials, gasPrice, gasLimit)
        val num = numberValue.value.toString()

        thread {
            val data =
                contract.store(
                    BigInteger(num),
                    BigInteger("${num}"),
                    "title${num}",
                    "content${num}",
                    "hash${num}-0",
                    "hash${num}-1"
                ).sendAsync()

            Log.d(TAG, "send result ${data.get().blockNumber}, ${data.get().gasUsed}")
            numberValue.postValue("0")
        }
    }

    fun readBtn_onClick() {
        Log.d(
            TAG,
            "MainFragment - MainFragmentVM - 버튼 클릭"
        )

        val contract =
            Forpawchain_sol_Storage.load(contractAddress, web3, credentials, gasPrice, gasLimit)
        val num = numberValue.value.toString()

        thread {
            // 값 읽기는 어떻게 읽는거냐-
            val size = contract.getSize().sendAsync().get()

            val remoteCall = contract.retrieve(BigInteger(num))
            val result = remoteCall.send()
            val value1 = result[0] as Uint256
            val value2 = result[1] as Uint256
            val value3 = result[2] as Utf8String
            val value4 = result[3] as Utf8String
            val value5 = result[4] as Utf8String
            val value6 = result[5] as Utf8String
            Log.d(TAG, "")
//            statusText.postValue(value.toString())
//            Log.d(TAG, "recv result ${value}")
        }


    }
}