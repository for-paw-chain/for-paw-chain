package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HouseFragmentVM : ViewModel() {
    companion object {
        const val TAG: String = "HouseFragmentVM"
    }

    val searchEditText = MutableLiveData<String>()
}