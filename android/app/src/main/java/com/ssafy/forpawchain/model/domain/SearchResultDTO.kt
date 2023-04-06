package com.ssafy.forpawchain.model.domain
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.RawValue
import java.io.Serializable

data class SearchResultDTO(
    // 코드 db에서는 pid
    var code: String?,
    // 사진
    var profile: @RawValue Drawable?,
    // 이름
    var name: String?,
    // 성별
    var sex: String?,
    // 종
    var species: String?,
    // 종류
    var kind: String?,
    // 중성화 여부 //db는 spayed
    var neutered: String?,
    // 생일
    var birth: String?,
    // 지역
    var region: String?,
    // 주인 전화번호
    var tel: String?,
    // 특이사항
    var etc: String?,

    ): Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        TODO("profile"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(sex)
        parcel.writeString(species)
        parcel.writeString(kind)
        parcel.writeString(neutered)
        parcel.writeString(birth)
        parcel.writeString(region)
        parcel.writeString(tel)
        parcel.writeString(etc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchResultDTO> {
        override fun createFromParcel(parcel: Parcel): SearchResultDTO {
            return SearchResultDTO(parcel)
        }

        override fun newArray(size: Int): Array<SearchResultDTO?> {
            return arrayOfNulls(size)
        }
    }
}