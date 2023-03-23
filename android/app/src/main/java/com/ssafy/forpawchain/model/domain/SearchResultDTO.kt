package com.ssafy.forpawchain.model.domain
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchResultDTO(
    // 이름
    var name: String,
    // 성별
    var sex: String,
    // 종
    var species: String,
    // 종류
    var kind: String,
    // 중성화 여부
    var neutered: String
): Parcelable