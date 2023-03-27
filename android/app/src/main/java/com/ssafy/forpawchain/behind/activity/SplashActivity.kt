package com.ssafy.forpawchain.behind.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.UserManager
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.ssafy.forpawchain.BuildConfig
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.fragment.UserFragment

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //해시 키가 컴퓨터마다 바뀌는 경우가 생김.. 그럴때 마다 하나씩 추가하기
//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        // Kakao SDK 초기화, local.properties에서 불러와야함
        Log.d(LoginActivity.TAG, "카카오 키는 제대로? ${BuildConfig.KAKAO_NATIVE_APP_KEY}")
        KakaoSdk.init(this, "${BuildConfig.KAKAO_NATIVE_APP_KEY}")
        startLoading();
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            // Splash Screen이 뜨고 나서 실행될 Activity 연결

            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    // 에러가 발생한 경우 처리합니다.
                    Log.d(UserFragment.TAG, "회원 탈퇴 에러 발생")
                } else {
                    // 로그아웃이 성공한 경우 처리합니다.
                    Log.d(UserFragment.TAG, "회원 탈퇴")
                }
            }

            UserApiClient.instance.logout { error ->
                if (error != null) {
                    // 에러가 발생한 경우 처리합니다.
                    Log.d(UserFragment.TAG, "로그아웃 에러 발생")
                } else {
                    // 로그아웃이 성공한 경우 처리합니다.
                    Log.d(UserFragment.TAG, "로그아웃")
                }
            }

            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if(error != null){
                    Log.d(LoginActivity.TAG, "로그인 토큰 에러>> ${error}")
                    startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }else if(tokenInfo != null){
                    Log.d(LoginActivity.TAG, "로그인 정상 >> ${tokenInfo}")
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
            }
        }, 2000)
    }
}