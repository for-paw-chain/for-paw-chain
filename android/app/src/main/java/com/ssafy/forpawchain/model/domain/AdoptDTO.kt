package com.ssafy.forpawchain.model.domain

import android.graphics.drawable.Drawable

data class AdoptDTO(
    var profile: Drawable?,

    // 종
    var species: String,
    // 종류
    var kind: String,
    // 중성화 여부
    var neutered: String
)
