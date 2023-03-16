package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.forpawchain.blockchain.Forpawchain_sol_Storage
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.infura.InfuraHttpService
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import android.util.Log
import kotlin.concurrent.thread


class PawFragmentVM : ViewModel() {
    val numberValue = MutableLiveData<String>("0")
    val statusText = MutableLiveData<String>("읽은 값")


    companion object {
        //        val web3 = Web3j.build(HttpService("http://183.97.128.216:7545/")) // defaults to http://localhost:8545/
        val TAG: String? = this::class.qualifiedName


        // 2849717dc6944af6a40ccf1540bdcb91
        val web3 =
            Web3j.build(InfuraHttpService("https://sepolia.infura.io/v3/2849717dc6944af6a40ccf1540bdcb91"))

        //        val web3 = Web3jFactory.build()
        val web3ClientVersion = web3.web3ClientVersion().sendAsync().get()

        // contract address
        val contractAddress = "0x8B15E3A60F8B9A447FA3E306562345AE64964A48"

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
            TAG,
            "MainFragment - MainFragmentVM - 버튼 클릭"
        )

        val contract =
            Forpawchain_sol_Storage.load(contractAddress, web3, credentials, gasPrice, gasLimit)

        thread {
            val data =
                contract.store(
                    BigInteger("100"),
                    BigInteger("100"), "title100", "content100", "hash100-0", "hash100-1"
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

        thread {
            // 값 읽기는 어떻게 읽는거냐-
            val number = contract.getNumber(BigInteger("0")).sendAsync().get()
            val date = contract.getDate(BigInteger("0")).sendAsync().get()
            val title = contract.getTitle(BigInteger("0")).sendAsync().get()
            val content = contract.getContent(BigInteger("0")).sendAsync().get()
            val hash1 = contract.getHash1(BigInteger("0")).sendAsync().get()
            val hash2 = contract.getHash2(BigInteger("0")).sendAsync().get()
            val size = contract.getSize().sendAsync().get()
            Log.d(TAG, "")
//            statusText.postValue(value.toString())
//            Log.d(TAG, "recv result ${value}")
        }


    }
}