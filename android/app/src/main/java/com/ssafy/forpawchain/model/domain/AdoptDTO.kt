package com.ssafy.forpawchain.model.domain

import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.Parcelize

data class AdoptDTO(
    var pid: MutableLiveData<String>,
    var profile: MutableLiveData<Drawable?>,

    // 종
    var species: MutableLiveData<String>,
    // 종류
    var kind: MutableLiveData<String>,
    // 중성화 여부
    var neutered: MutableLiveData<String>

)
