package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HouseFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    val searchEditText = MutableLiveData<String>()
}