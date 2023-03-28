package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.DianosisNewDTO
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DiagnosisDetailFragmentVM : ViewModel() {
    val name = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val date = MutableLiveData<String>()
    val searchEditText = MutableLiveData<String>("410087800324555")

    //추가 시작
    val todoLiveData = MutableLiveData<List<DianosisNewDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<DianosisNewDTO>()

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun addTask(todo: DianosisNewDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: DianosisNewDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }

}