package com.ssafy.forpawchain.viewmodel.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO


class MyPawFragmentVM : ViewModel() {
    companion object {
        val TAG: String? = this::class.qualifiedName

    }

    //추가 시작
    val todoLiveData = MutableLiveData<List<MyPawListDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<MyPawListDTO>()

    fun addTask(todo: MyPawListDTO) {
        data.add(todo)
        todoLiveData.value = data //todoLiveData를 add된 데이터로 변경
    }

    fun deleteTask(todo: MyPawListDTO) {
        data.remove(todo)
        todoLiveData.value = data //todoLiveData를 remove된 데이터로 변경, 이제 TodoLiveData로 UI값을 변경해줘야한다.
    }

    fun clearTask() {
        data.clear()
        todoLiveData.value = data
    }
}