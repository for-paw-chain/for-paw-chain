package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class HouseFragmentVM : ViewModel() {
    val message = MutableLiveData<String>("하우스 프래그먼트입니다!")
}