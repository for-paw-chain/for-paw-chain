package com.ssafy.forpawchain.model.domain

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData

data class DianosisNewDTO(
    // 종류
    var title: MutableLiveData<String>,
    // 중성화 여부
    var body: MutableLiveData<String>
)
