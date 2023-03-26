package com.ssafy.forpawchain.behind.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import com.ssafy.forpawchain.BuildConfig
import com.ssafy.forpawchain.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Kakao SDK 초기화, local.properties에서 불러와야함
        //val kakaoNativeAppKey = getKakaoNativeAppKey(this)
        Log.d(LoginActivity.TAG, "카카오 키는 제대로? ${BuildConfig.KAKAO_NATIVE_APP_KEY}")
        KakaoSdk.init(this, "${BuildConfig.KAKAO_NATIVE_APP_KEY}")
        startLoading();
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            // Splash Screen이 뜨고 나서 실행될 Activity 연결
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