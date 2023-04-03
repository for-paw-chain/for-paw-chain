package com.ssafy.forpawchain.behind.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.UserManager
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.ssafy.forpawchain.BuildConfig
import com.ssafy.forpawchain.R

import com.ssafy.forpawchain.behind.fragment.UserFragment
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.blockchain.Test_sol_ForPawChain
import com.ssafy.forpawchain.blockchain.Test_sol_MyContract
import com.ssafy.forpawchain.model.domain.UserDTO
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.UserService
import com.ssafy.forpawchain.util.PreferenceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.infura.InfuraHttpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import kotlin.concurrent.thread

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //해시 키가 컴퓨터마다 바뀌는 경우가 생김.. 그럴때 마다 하나씩 추가하기
//        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)

        // Kakao SDK 초기화, local.properties에서 불러와야함
        Log.d(LoginActivity.TAG, "카카오 키는 제대로? ${BuildConfig.KAKAO_NATIVE_APP_KEY}")
        KakaoSdk.init(this, "${BuildConfig.KAKAO_NATIVE_APP_KEY}")

        ForPawChain.setBlockChain(
            "0x789bE5eC74330cd64d007a15bD273fCC27fEE6bB",
            "6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544"
        )
//        val temp = ForPawChain.getHistory()
        startLoading();
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            // Splash Screen이 뜨고 나서 실행될 Activity 연결

            /**
             * 카카오 로그인 부분
             * **/

            // unlink는 회원탈퇴
//            UserApiClient.instance.unlink { error ->
//                if (error != null) {
//                    Log.d(UserFragment.TAG, "회원 탈퇴 에러 발생")
//                } else {
//                    Log.d(UserFragment.TAG, "회원 탈퇴")
//                }
//            }
//
//            로그아웃
//            UserApiClient.instance.logout { error ->
//                if (error != null) {
//                    // 에러가 발생한 경우 처리합니다.
//                    Log.d(UserFragment.TAG, "로그아웃 에러 발생")
//                } else {
//                    // 로그아웃이 성공한 경우 처리합니다.
//                    Log.d(UserFragment.TAG, "로그아웃")
//                }
//            }
//
//            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                if(error != null){
//                    Log.d(LoginActivity.TAG, "로그인 토큰 에러>> ${error}")
////                    startActivity(Intent(applicationContext, LoginActivity::class.java))
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    finish()
//                }else if(tokenInfo != null){
//                    Log.d(LoginActivity.TAG, "로그인 정상 >> ${tokenInfo}")
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    finish()
//                }
//            }

            // 포포체인 서비스 로그인 테스트를 위해 매번 로그아웃

            val sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("uid", null)

            val token = sharedPreferences.getString("token", "")!!

            GlobalScope.launch {
                val response = withContext(Dispatchers.IO) {
                    UserService().logoutUser(token).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                        ) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성공된 경우
                                lifecycleScope.launch {

                                }
                                // call
                                Log.d(TAG, "로그 아웃 성공 "+ response);

                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d(TAG, "로그 아웃 실패 " + response)
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(TAG, "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
            }

            Log.d(TAG, "초기화 전 모든 값 출력 ");
            PreferenceManager().printAll(applicationContext)

            //매번 초기화
            PreferenceManager().clear(applicationContext)

            Log.d(TAG, "초기화 후 모든 값 출력 ");
            PreferenceManager().printAll(applicationContext)

            startActivity(Intent(applicationContext, LoginActivity::class.java))
            /**
            // 처음 로그인인지 아닌지 확인
            val sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE)

            // 처음 로그인인 경우, sharedPreferences는 null 인 경우가 없으므로 다르게 판단
            val uid = sharedPreferences.getString("uid", null)
            if (uid == null) {  // uid가 없는 경우 -> 처음 로그인
                Log.d(TAG, "처음 로그인")
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            } else {  // uid가 있는 경우 -> 로그인 이력이 있는 경우
                Log.d(TAG, "이전 로그인 정보가 존재함")
                Log.d(TAG, "uid: $uid")
                nextMainActivity()
            }
             **/

            /**

            GlobalScope.launch {
                val response = withContext(Dispatchers.IO) {
                    UserService().getUser(applicationContext).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(
                            call: Call<JsonObject>,
                            response: Response<JsonObject>
                        ) {
                            if (response.isSuccessful) {
                                // 처음 로그인이 아닌 경우
                                lifecycleScope.launch {
                                    UserInfo.setUserInfo(response.body().toString())
                                }
                                // call
                                Log.d(TAG, "로그인 성공 ${response.body()}" );
                                nextMainActivity()

                            } else {
                                // 처음 로그인 한 경우 (응답코드 3xx, 4xx 등)
                                Log.d(TAG, "로그인 실패 ${response.body()}" )
                                startActivity(Intent(applicationContext, LoginActivity::class.java))
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(TAG, "onFailure 에러: " + t.message.toString());
                        }
                    })
                }
            }**/
        }, 2000)
    }
}