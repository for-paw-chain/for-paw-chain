package com.ssafy.forpawchain.model.room
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import com.kakao.sdk.user.model.AccessTokenInfo

//import com.ssafy.forpawchain.model.domain.User

class UserInfo {
        companion object {
            var uid: String = "6"
            var name: String = "포포체인"
            var profile: String = ""
            var token: String = ""
            lateinit var privateKey: String

             //정상 코드
            fun setUserInfo(user: User, Otoken: OAuthToken) {
                uid = user.id.toString()
                name = user.properties!!["nickname"] ?: ""
                profile = user.properties!!["profile_image"] ?: ""
                token = Otoken.accessToken
            }

//            // 더미 데이터
//            fun setUserInfo(user: User, Otoken: OAuthToken) {
//                uid = "6"
//                name = "포포체인"
//                profile = "https://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_640x640.jpg"
//                token = Otoken.accessToken
//            }
        }
}