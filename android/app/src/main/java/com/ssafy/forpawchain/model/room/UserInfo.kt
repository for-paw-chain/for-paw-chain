package com.ssafy.forpawchain.model.room

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout.TabGravity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.model.User
import com.kakao.sdk.user.model.AccessTokenInfo
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bouncycastle.crypto.tls.CipherType.stream
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

//import com.ssafy.forpawchain.model.domain.User

class UserInfo {
    companion object {

        val TAG: String? = this::class.qualifiedName

        var uid: String = "" // 서버의 primary key
        var id: String = ""  // socail 로그인이 주는 id
        var profile: Drawable? = null
        var name: String = ""
        var walletAddress: String = ""
        var isDoctor: Boolean = false
        var token: String = ""
        lateinit var privateKey: String

        //정상 코드
        suspend fun setUserInfo(jsonResponse: String, token: String, context: Context) {
            val gson = Gson()
            val jsonObject = gson.fromJson(jsonResponse, JsonObject::class.java)

            val profileUrl = jsonObject.get("profile").asString

            Log.d(TAG, "프로필은 : ${profileUrl}")

            var drawable: Drawable? = null

            withContext(Dispatchers.IO) {
                drawable = Glide.with(context)
                    .load(profileUrl)
                    .apply(RequestOptions().transform(RoundedCorners(50)))
                    .submit()
                    .get()
            }

            this.uid = jsonObject.get("uid").asString
            this.id = jsonObject.get("id").asString
//                this.profile = Drawable.createFromPath(jsonObject.get("profile").toString())
            this.profile = drawable
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
            uid = "1"
            name = "윤혜진"
            token = Otoken.replace("\"", "")
        }
    }
}