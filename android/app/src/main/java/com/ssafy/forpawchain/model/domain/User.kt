package com.ssafy.forpawchain.model.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var uid: String,
    var privateKey: String
)