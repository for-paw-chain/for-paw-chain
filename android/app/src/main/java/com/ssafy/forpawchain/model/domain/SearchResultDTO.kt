package com.ssafy.forpawchain.model.domain
import android.graphics.drawable.Drawable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import java.io.Serializable

data class SearchResultDTO(
    // 코드 db에서는 pid
    var code: String,
    // 사진
    var profile: @RawValue Drawable?,
    // 이름
    var name: String,
    // 성별
    var sex: String,
    // 종
    var species: String,
    // 종류
    var kind: String,
    // 중성화 여부 //db는 spayed
    var neutered: String,
    // 생일
    var birth : String?,
    // 지역
    var region : String?,
    // 주인 전화번호
    var tel : String?,
    // 특이사항
    var etc : String?,

): Serializable