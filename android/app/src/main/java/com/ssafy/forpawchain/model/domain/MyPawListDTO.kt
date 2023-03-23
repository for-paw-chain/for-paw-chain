package com.ssafy.forpawchain.model.domain


data class MyPawListDTO(
    // 이름
    var name: MutableLiveData<String>,
    // 성별
    var sex: MutableLiveData<String>,
    // 종
    var species: MutableLiveData<String>,
    // 종류
    var kind: MutableLiveData<String>,
    // 중성화 여부
    var neutered: String
)