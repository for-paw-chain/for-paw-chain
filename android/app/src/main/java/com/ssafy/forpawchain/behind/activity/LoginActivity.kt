package com.ssafy.forpawchain.behind.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.BuildConfig
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ActivityLoginBinding
import com.ssafy.forpawchain.viewmodel.activity.LoginVM
import java.util.Properties

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    //뒤로가기 연속 클릭 대기 시간
    private var backPressedTime: Long = 0

    companion object {
        val TAG: String? = this::class.qualifiedName
    }
    // Log.d(TAG, "LoginActivity - loginVM - pwEditText 라이브 데이터 값 변경 : $it")

//    private val viewModel: LoginVM by viewModels()

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val mCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.d(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.d(TAG, "로그인 성공 ${token.accessToken}")
            nextMainActivity()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.idKakaoLoginBtn.id -> {
                if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                    UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                        if (error != null) {
                            Log.d(TAG, "로그인 실패 $error")
                            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                return@loginWithKakaoTalk
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                            }
                        } else if (token != null) {
                            Log.d(TAG, "로그인 성공 ${token.accessToken}")
                            Toast.makeText(this, "로그인 성공!", Toast.LENGTH_SHORT).show()
                            nextMainActivity()
                        }
                    }
                } else {
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = mCallback)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        // 안드로이드 기본 메뉴 바 숨기는 코드, 있으면 이상함
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        // Kakao SDK 초기화
        //kakao_native_app_key

//        Log.d(TAG, "카카오 로그인 네이티브 키 ${getApplicationContext().getResources().getString(R.string.kakao_native_app_key)}")
//        KakaoSdk.init(this, "${R.string.kakao_native_app_key}")

        //액세스 토큰이 있는 경우
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error == null) {
                    nextMainActivity()
                }
            }
        }

        setContentView(binding.root)
        binding.idKakaoLoginBtn.setOnClickListener(this)
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        Log.d(TAG, "뒤로가기")

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            android.os.Process.killProcess(android.os.Process.myPid());
            return
        }
        // 처음 클릭 메시지
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    private fun nextMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun getKakaoNativeAppKey(context: LoginActivity): String? {
        val properties = Properties()
        val inputStream = context.assets.open("local.properties")
        properties.load(inputStream)
        Log.d(TAG, "제발 카카오 로그인 >> ${properties.getProperty("kakao_native_app_key")}")
        return properties.getProperty("kakao_native_app_key")
    }

//    private fun initObserve() {
//        viewModel.openEvent.eventObserve(this) { obj ->
//
//            var intent: Intent? = null
//
//            when (obj) {
//                ActivityCode.MAIN_ACTIVITY -> intent = Intent(this, MainActivity::class.java)
//                else -> {
//                    null
//                }
//            }
//
//            startActivity(intent)
//        }
//    }
}