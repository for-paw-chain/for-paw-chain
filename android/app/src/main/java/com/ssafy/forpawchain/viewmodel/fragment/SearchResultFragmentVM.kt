package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class SearchResultFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    val name = MutableLiveData<String>()

    val sex = MutableLiveData<String>()

    val species = MutableLiveData<String>()

    val kind = MutableLiveData<String>()

    val neutered = MutableLiveData<Int>()

}