package com.ssafy.forpawchain.viewmodel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.Event
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.model.domain.Data
import com.ssafy.forpawchain.model.domain.DianosisNewDTO


class CreateDoctorHistoryFragmentVM : ViewModel() {
    private val _openEvent = MutableLiveData<Event<ActivityCode>>()
    val openEvent: LiveData<Event<ActivityCode>> get() = _openEvent
    val number = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val body = MutableLiveData<String>()
    val path = MutableLiveData<String>()

    //추가 시작
    val todoLiveData = MutableLiveData<List<DianosisNewDTO>>() //변경/관찰가능한 List

    //추가 끝
    private val data = arrayListOf<DianosisNewDTO>()


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

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    fun doneBtn_onClick() {
        var list: ArrayList<Data> = ArrayList()
        for (item in data) {
            list.add(Data(item.title.value, item.body.value))
            Log.d(TAG, "item: ${item}")
        }

        title.value?.let {
            body.value?.let { it1 ->
                ForPawChain.createHistory(
                    it,
                    it1, list, "hash"
                )
            }
        }

        clearTask()
        _openEvent.value = Event(ActivityCode.DONE)
    }


    fun addForm_onClick() {
        addTask(DianosisNewDTO(MutableLiveData<String>(""), MutableLiveData<String>("")))
    }

    fun imageSelect_onClick() {

    }
}