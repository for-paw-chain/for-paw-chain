package com.ssafy.forpawchain.model.domain

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

data class MyPawListDTO(
    // 코드
    var code: MutableLiveData<String>,
    // 사진
    var profile: MutableLiveData<Drawable>?,

    // 이름
    var name: String,
    // 성별
    var sex: String,
    // 종
    var species: String,
    // 종류
    var kind: String,
    // 중성화 여부
    var neutered: MutableLiveData<String>
) : Serializable