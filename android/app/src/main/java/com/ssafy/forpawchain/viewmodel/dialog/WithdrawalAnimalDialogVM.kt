package com.ssafy.forpawchain.viewmodel.dialog

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WithdrawalAnimalDialogVM : ViewModel() {
    val image = MutableLiveData<Drawable>()
    val name = MutableLiveData<String>()
    val code = MutableLiveData<String>()
    val date = MutableLiveData<String>()
}