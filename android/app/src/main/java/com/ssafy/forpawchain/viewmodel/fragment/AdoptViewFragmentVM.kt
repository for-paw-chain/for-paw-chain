package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.blockchain.Forpawchain_sol_Storage
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.DiagnosisHistoryDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread


class AdoptViewFragmentVM : ViewModel() {
    val extra = MutableLiveData<String>("초기 특이사항이예요\n별이 잘부탁드려요~!!!\n카와이 별쨩!")

    //추가 시작
    val todoLiveData = MutableLiveData<List<DiagnosisHistoryDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<DiagnosisHistoryDTO>()

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun addTask(todo: DiagnosisHistoryDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: DiagnosisHistoryDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }
}