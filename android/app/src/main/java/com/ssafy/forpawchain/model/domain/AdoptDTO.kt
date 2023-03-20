package com.ssafy.forpawchain.model.domain

data class AdoptDTO(
    // 종
    var species: String,
    // 종류
    var kind: String,
    // 중성화 여부
    var neutered: String
)
