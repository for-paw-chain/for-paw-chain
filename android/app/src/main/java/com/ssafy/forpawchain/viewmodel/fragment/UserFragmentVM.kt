package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.forpawchain.viewmodel.activity.LoginVM
import com.ssafy.forpawchain.blockchain.Contract_sol_Storage
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import java.math.BigInteger
import kotlin.concurrent.thread


class UserFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    private val data = arrayListOf<MyPageMenuDTO>()
    val todoLiveData = MutableLiveData<List<MyPageMenuDTO>>() //변경/관찰가능한 List
    fun addTask(todo: MyPageMenuDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: MyPageMenuDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }

    val name = MutableLiveData<String>("홍길동")
    val code = MutableLiveData<String>("#043261")
}