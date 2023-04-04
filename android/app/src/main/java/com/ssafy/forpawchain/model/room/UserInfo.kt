package com.ssafy.forpawchain.model.room
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import com.kakao.sdk.user.model.AccessTokenInfo
import com.ssafy.forpawchain.util.PreferenceManager

//import com.ssafy.forpawchain.model.domain.User

class UserInfo {
        companion object {
            var uid: String = "" // 서버의 primary key
            var id: String = ""  // socail 로그인이 주는 id
            var profile: String = ""
            var name: String = ""
            var walletAddress: String = ""
            var isDoctor: Boolean = false
            var token: String = ""
            lateinit var privateKey: String

            //정상 코드
            fun setUserInfo(jsonResponse: String, token: String) {
                val gson = Gson()
                val jsonObject = gson.fromJson(jsonResponse, JsonObject::class.java)

                this.uid = jsonObject.get("uid").asString
                this.id = jsonObject.get("id").asString
                this.profile = jsonObject.get("profile").asString
                this.name = jsonObject.get("name").asString
                if (jsonObject.get("wa").isJsonNull()) {
                    this.walletAddress = ""
                } else {
                    this.walletAddress = jsonObject.get("wa").asString
                }
                this.isDoctor = jsonObject.get("doctor").asBoolean
                this.token = token
            }

            // 테스트 로그인
            fun setTestUserInfo(Otoken: String) {
                uid = "54"
                name = "윤혜진"
                token = Otoken.replace("\"", "")
            }
        }
}