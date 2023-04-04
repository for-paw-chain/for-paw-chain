package com.ssafy.forpawchain.model.domain

data class UserDTO(
    var uid: String,
    var id: String,
    var profile: String,
    var name: String,
    var walletAddress: String,
    var isDoctor: Boolean,
)
