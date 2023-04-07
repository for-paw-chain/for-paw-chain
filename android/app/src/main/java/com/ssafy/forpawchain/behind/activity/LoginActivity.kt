package com.ssafy.forpawchain.behind.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.ssafy.forpawchain.databinding.ActivityLoginBinding
import com.ssafy.forpawchain.model.domain.LoginUserReqDTO
import com.ssafy.forpawchain.model.domain.User
import com.ssafy.forpawchain.model.domain.UserDTO
import com.ssafy.forpawchain.model.domain.signUpRequestDTO
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.room.UserInfo.Companion.setUserInfo
import com.ssafy.forpawchain.model.room.UserInfo.Companion.walletAddress
import com.ssafy.forpawchain.model.service.UserService
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    //뒤로가기 연속 클릭 대기 시간
    private var backPressedTime: Long = 0
//    private lateinit var context: Context

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)

        // context 초기화
//        context = this

        // 안드로이드 기본 메뉴 바 숨기는 코드, 있으면 이상함
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        //화면 띄어 주는 것이라서 지우면 안됨
        setContentView(binding.root)

        binding.idKakaoLoginBtn.setOnClickListener {
            btnKakaoLogin(it)
        }

        binding.imageView2.setOnClickListener {
            generalLogin()
        }
    }

    fun generalLogin() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) {
                val loginUserReqDTO = LoginUserReqDTO(
                    "2729484551", "KAKAO"
                )

                Log.d(TAG, "일반 로그인 ${loginUserReqDTO}")
                UserService().generalLogin(loginUserReqDTO)
                    .enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>,
                        ) {
                            if (response.isSuccessful) {
                                lifecycleScope.launch {
                                    UserInfo.setTestUserInfo(
                                        response.body()?.get("accessToken").toString()
                                    )
                                    PreferenceManager().setString(
                                        applicationContext,
                                        "token",
                                        response.body()?.get("accessToken")?.asString ?: ""
                                    )
                                }
                                call
                                Log.d(TAG, "일반 로그인 시 응답 " + response.body())
                                Log.d(TAG, "일반 로그인 성공 " + response);
                                nextMainActivity()

                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d(TAG, "일반 로그인 실패 " + response)
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(TAG, "onFailure 에러: " + t.message.toString());
                        }

                    })
            }
        }
    }

    fun btnKakaoLogin(view: View) {
        // 처음 로그인인지 아닌지 확인
        // 처음 로그인인 경우, sharedPreferences는 null 인 경우가 없으므로 다르게 판단

        val uid = PreferenceManager().getString(applicationContext, "uid")
        Log.d(TAG, "uid = ? ${uid}")

        Log.d(TAG, "printAll")
        PreferenceManager().printAll(applicationContext)

        if (uid.isNullOrEmpty()) {  // uid가 없는 경우 -> 처음 로그인
            Log.d(TAG, "처음 로그인")
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
                        Log.d("[카카오톡 로그인]", "로그인에 성공하였습니다. 카카오 토큰은 > ${token.accessToken}")
                        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                            UserApiClient.instance.me { user, error ->
                                Log.d("[카카오톡 로그인]", "유저 정보. ${user}")
                                Log.d("[카카오톡 로그인]", "토큰 정보. ${token}")
                                // 첫 로그인인지 아닌지 구분, signUpUser해야함
                                CoroutineScope(Dispatchers.IO).launch {
                                    try {
                                        val response = withContext(Dispatchers.IO) {
                                            val signUpRequestDTO = signUpRequestDTO(
                                                user!!.id.toString(),
                                                user.properties!!["nickname"] ?: "",
                                                user.properties!!["profile_image"] ?: "",
                                                "KAKAO"
                                            )
                                            Log.d(TAG, "카카오 톡 첫 로그인 ${signUpRequestDTO}")
                                            //처음 로그인

                                            UserService().signUpAndLoginUser(signUpRequestDTO)
                                                .enqueue(object :
                                                    Callback<JsonObject> {
                                                    override fun onResponse(
                                                        call: Call<JsonObject>,
                                                        response: Response<JsonObject>,
                                                    ) {
                                                        if (response.isSuccessful) {
//                                              정상적으로 통신이 성공된 경우

                                                            /**
                                                            CoroutineScope에서 비동기적인 작업을 수행하면 해당 작업은 백그라운드 스레드에서 실행
                                                            그리고 비동기를 안할 경우 해당 작업이 UI 스레드에서 실행되어 느린 응답과
                                                            ANR(Application Not Responding) 문제 발생

                                                            따라서, 비동기적인 작업을 수행하는 것이 좋으며
                                                            이는 UI 스레드가 블록되지 않고, 빠른 앱 응답으로 더 나은 사용자 경험 제공
                                                            CoroutineScope는 비동기 작업을 보다 쉽고 안전하게 처리할 수 있도록 도와주는 도구
                                                             **/

                                                            Log.d(
                                                                TAG,
                                                                "회원 가입 성공, 서버 응답은 ${response.body()}"
                                                            );
                                                            lifecycleScope.launch {
                                                                // 회원 가입에서 받은 accessToken으로 getUser()하기

                                                                // PreferenceManager 클래스 인스턴스 생성
                                                                val preferenceManager =
                                                                    PreferenceManager()

                                                                // 문자열 데이터 저장
                                                                preferenceManager.setString(
                                                                    applicationContext,
                                                                    "token",
                                                                    response.body()
                                                                        ?.get("accessToken")?.asString
                                                                        ?: ""
                                                                )
                                                                Log.d(
                                                                    TAG,
                                                                    "getString 토큰 저장 잘 됨?" + preferenceManager.getString(
                                                                        applicationContext,
                                                                        "token"
                                                                    )
                                                                );
                                                                UserInfo.token = response.body()
                                                                    ?.get("accessToken")?.asString
                                                                    ?: ""

                                                                //회원가입에서 얻은 액세스 토큰으로 로그인 진행
                                                                login(
                                                                    preferenceManager.getString(
                                                                        applicationContext,
                                                                        "token"
                                                                    )!!
                                                                )
                                                            }
                                                        } else {
                                                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                                            Log.d(TAG, "회원 가입 실패 " + response)
                                                        }
                                                    }

                                                    override fun onFailure(
                                                        call: Call<JsonObject>,
                                                        t: Throwable,
                                                    ) {
                                                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                                        Log.d(
                                                            TAG,
                                                            "onFailure 에러: " + t.message.toString()
                                                        );
                                                    }
                                                })
                                        }
                                        Log.d(TAG, "카카오 톡 로그인 후 응답" + response)
                                    } catch (e: Exception) {
                                        Log.d(TAG, "익셉션 에러 " + e.message.toString())
                                    }

                                }
                            }
                        }
                    } else {
                        Log.d("카카오 로그인", "토큰==null error==null")
                    }
                }
            } else { // 카카오 계정 로그인
                UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoCallback)
            }

        } else {  // uid가 있는 경우 -> 로그인 이력이 있는 경우
            Log.d(TAG, "이전 로그인 정보가 존재함")
            Log.d(TAG, "uid: $uid")
            nextMainActivity()
        }
    }

    // 이메일 로그인 콜백
    private val kakaoCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오 계정 로그인 실패 $error")
        } else if (token != null) {
            Log.e(TAG, "카카오 계정 로그인 성공 ${token.accessToken}")
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                UserApiClient.instance.me { user, error ->
                    Log.d("[카카오 계정 로그인]", "유저 정보. ${user}")
                    Log.d("[카카오 계정 로그인]", "토큰 정보. ${token}")
                    // 첫 로그인인지 아닌지 구분, signUpUser해야함
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val signUpRequestDTO = signUpRequestDTO(
                                user!!.id.toString(),
                                user.properties!!["nickname"] ?: "",
                                user.properties!!["profile_image"] ?: "",
                                "KAKAO"
                            )
                            Log.d(TAG, "카카오 계정 첫 로그인 ${signUpRequestDTO}")
                            val response =
                                UserService().signUpAndLoginUser(signUpRequestDTO).execute()

                            Log.d("카카오 계정", " 서버 signUpAndLoginUser 응답 " + response.body().toString())

                            if (response.isSuccessful) {
                                Log.d("카카오 계정", " response.isSuccessful 오냐 " + response.isSuccessful)

                                val accessToken = response.body()?.get("accessToken")?.asString
                                Log.d("카카오 계정", " accessToken 은 뭐냐 " + accessToken)

                                if (accessToken != null) {
                                    val preferenceManager = PreferenceManager()
                                    preferenceManager.setString(
                                        applicationContext,
                                        "token",
                                        accessToken
                                    )
                                } else {
                                    Log.e(TAG, "토큰이 null입니다.")
                                }
                            } else {
                                Log.e(TAG, "회원 가입 실패 " + response)
                            }
                            Log.d("카카오 계정", "카카오 계정 로그인 후 응답" + response)
                            if(response.isSuccessful){
                                Log.d("카카오 계정", "PreferenceManager().getString token은?? " + PreferenceManager().getString(applicationContext, "token"))
                                UserInfo.token = PreferenceManager().getString(applicationContext, "token")!!
                                login(UserInfo.token)
                            }else {
                                // 로그인 실패 (응답코드 3xx, 4xx 등)
                                Log.d(TAG, "로그인 실패 response ${response}")
                                Log.d(TAG, "로그인 실패 response.body() ${response.body()}")
                                startActivity(Intent(applicationContext, SplashActivity::class.java))
                            }
                        } catch (e: Exception) {
                            Log.d(TAG, "익셉션 에러 " + e.message.toString())
                        }
                    }
                }
            }
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

    fun login(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response1 = withContext(Dispatchers.IO) {
                    UserService().getUser(token).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>,
                        ) {
                            Log.d("login 함수의 onResponse 응답1 ", response.body().toString())
                            Log.d("login 함수의 onResponse 응답2 ", response.toString())

                            if (response.isSuccessful) {
                                CoroutineScope(Dispatchers.IO).launch {
                                    Log.d("login 함수 response.isSuccessful ", "로그인 성공 ${response.body()}");
                                    UserInfo.setUserInfo(
                                        response.body().toString(),
                                        PreferenceManager().getString(applicationContext, "token")
                                            ?: "",
                                        applicationContext
                                    )

                                    PreferenceManager().setString(
                                        applicationContext,
                                        "uid",
                                        response.body()?.get("uid")?.asString ?: ""
                                    )
                                    PreferenceManager().setString(
                                        applicationContext,
                                        "id",
                                        response.body()?.get("id")?.asString ?: ""
                                    )
                                    PreferenceManager().setString(
                                        applicationContext,
                                        "profile",
                                        response.body()?.get("profile")?.asString ?: ""
                                    )
                                    PreferenceManager().setString(
                                        applicationContext,
                                        "name",
                                        response.body()?.get("name")?.asString ?: ""
                                    )

                                    if (walletAddress.isNullOrEmpty()) {
                                        PreferenceManager().setString(
                                            applicationContext,
                                            "walletAddress",
                                            ""
                                        )
                                    } else {
                                        PreferenceManager().setString(
                                            applicationContext,
                                            "walletAddress",
                                            response.body()?.get("wa")?.asString ?: ""
                                        )
                                    }

                                    PreferenceManager().setBoolean(
                                        applicationContext,
                                        "isDoctor",
                                        response.body()?.get("doctor")?.asBoolean ?: false
                                    )

                                    Log.d(TAG, "값 다 잘 들어갔냐 모든 값 출력 ");
                                    PreferenceManager().printAll(applicationContext)
                                    nextMainActivity()
                                }
//                                call

                            } else {
                                // 로그인 실패 (응답코드 3xx, 4xx 등)
                                Log.d(TAG, "로그인 실패 response ${response}")
                                Log.d(TAG, "로그인 실패 response.body() ${response.body()}")
                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                            }
                        }


                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(SplashActivity.TAG, "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
                Log.d("login 함수의 response 로그2 ", response1.toString())

            }catch (e: Exception){
                Log.d("로그인 함수", "익셉션 에러 " + e.message.toString())
            }


        }
    }
}