package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DiagnosisDetailFragmentVM : ViewModel() {
    val name = MutableLiveData<String>("Sign by 김의사")
    val title = MutableLiveData<String>("[초진] 치과수술 / 담석 체크 / 좌측 pm4 발치")
    val date = MutableLiveData<String>("2022-03-03 오후 03:05:27")
    val searchEditText = MutableLiveData<String>("410087800324555")

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

}