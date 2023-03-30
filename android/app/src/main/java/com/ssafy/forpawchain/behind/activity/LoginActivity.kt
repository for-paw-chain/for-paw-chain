package com.ssafy.forpawchain.behind.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.forpawchain.databinding.ActivityLoginBinding
import com.ssafy.forpawchain.model.room.UserInfo


class LoginActivity : AppCompatActivity() {
    //뒤로가기 연속 클릭 대기 시간
    private var backPressedTime: Long = 0

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    // 이메일 로그인 콜백
    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "로그인 실패 $error")
        } else if (token != null) {
            Log.e(TAG, "로그인 성공 ${token.accessToken}")
            nextMainActivity()
        }
    }

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        // 안드로이드 기본 메뉴 바 숨기는 코드, 있으면 이상함
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        //화면 띄어 주는 것이라서 지우면 안됨
        setContentView(binding.root)

        binding.idKakaoLoginBtn.setOnClickListener {
            btnKakaoLogin(it)
        }
    }

    fun btnKakaoLogin(view: View) {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오 계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                //로그인 실패 부분
                if (error != null) {
                    when {
                        error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "접근이 거부 됨(동의 취소)")
                        }
                        error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "유효하지 않은 앱")
                        }
                        error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "인증 수단이 유효하지 않아 인증할 수 없는 상태")
                        }
                        error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "요청 파라미터 오류")
                        }
                        error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "유효하지 않은 scope ID")
                        }
                        error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "설정이 올바르지 않음(android key hash)")
                        }
                        error.toString() == AuthErrorCause.ServerError.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "서버 내부 에러")
                        }
                        error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                            Log.d("[카카오톡 로그인 에러]", "앱이 요청 권한이 없음")
                        }
                        else -> { // Unknown
                            Log.d("[카카오톡 로그인 에러]", "기타 에러 ${error.toString()}")
                        }
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                }
                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                else if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                } else if (token != null) {
                    Log.d("[카카오톡 로그인]", "로그인에 성공하였습니다. 토큰은 > ${token.accessToken}")
                    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                        UserApiClient.instance.me { user, error ->
                            Log.d("[카카오톡 로그인]", "유저 정보. ${user}")
                            Log.d("[카카오톡 로그인]", "토큰 정보. ${token}")
                            UserInfo.parseUser(user!!)

                        }
                    }

                    nextMainActivity()
                } else {
                    Log.d("카카오 로그인", "토큰==null error==null")
                }
            }
        } else { // 카카오 계정 로그인
            Log.d("카카오 계정 로그인", "잘 나옴?")
            UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
        }
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

    // 카카오 웹뷰 로그인을 위한 오버라이딩 함수
//    fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//        Log.d(LoginActivity.TAG, "shouldOverrideUrlLoading :: $url")
//        try {
//            /**
//             * 20 12 29
//             * 카카오링크 오류 수정을 위해 아래 if문을 추가함.
//             */
//            if (url != null && url.startsWith("intent:kakaolink:")) {
//                try {
//                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
//                    val existPackage =
//                        packageManager.getLaunchIntentForPackage(intent.getPackage()!!)
//                    if (existPackage != null) {
//                        startActivity(intent)
//                    } else {
//                        val marketIntent = Intent(Intent.ACTION_VIEW)
//                        marketIntent.data = Uri.parse("market://details?id=" + intent.getPackage())
//                        startActivity(marketIntent)
//                    }
//                    return true
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//        return false
//    }

}