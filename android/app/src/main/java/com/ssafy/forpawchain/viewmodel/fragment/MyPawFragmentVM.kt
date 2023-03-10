package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO


class MyPawFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    //추가 시작
    val todoLiveData = MutableLiveData<List<SearchResultDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<SearchResultDTO>()
}