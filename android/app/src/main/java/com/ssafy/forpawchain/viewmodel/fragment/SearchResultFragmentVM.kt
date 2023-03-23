package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.model.domain.DiagnosisHistoryDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO

class SearchResultFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent

    val selectedSearchResult = MutableLiveData<SearchResultDTO>() // 변경, 관찰 가능한 List

}