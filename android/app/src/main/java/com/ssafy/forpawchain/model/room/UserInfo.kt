package com.ssafy.forpawchain.model.room
import com.kakao.sdk.user.model.User

//import com.ssafy.forpawchain.model.domain.User

class UserInfo {
        companion object {
            var uid: String = "6"
            var name: String = "포포체인"
            var profile: String = ""
            lateinit var privateKey: String

            fun parseUser(user: User) {
                uid = user.id.toString()
                name = user.properties!!["nickname"] ?: ""
                profile = user.properties!!["profile_image"] ?: ""
            }
        }
}