package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


class PawFragmentVM : ViewModel() {
    val selectedKind = MutableLiveData<Int>()
    val selectedNeutered = MutableLiveData<Int>()
    val selectedSex = MutableLiveData<Int>()

    //추가 시작
    val todoLiveData = MutableLiveData<List<AdoptDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<AdoptDTO>()

    fun addTask(todo: AdoptDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: AdoptDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }
}