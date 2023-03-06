package com.ssafy.forpawchain.behind.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ActivityLoginBinding
import com.ssafy.forpawchain.viewmodel.activity.LoginVM

class LoginActivity : AppCompatActivity() {
    //뒤로가기 연속 클릭 대기 시간
    private var backPressedTime: Long = 0

    companion object {
        val TAG: String? = this::class.qualifiedName
    }
    // Log.d(TAG, "LoginActivity - loginVM - pwEditText 라이브 데이터 값 변경 : $it")

    private val viewModel: LoginVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initObserve()
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
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

    private fun initObserve() {
        viewModel.openEvent.eventObserve(this) { obj ->

            var intent: Intent? = null

            when (obj) {
                ActivityCode.MAIN_ACTIVITY -> intent = Intent(this, MainActivity::class.java)
                ActivityCode.LOGIN_ACTIVITY -> intent = Intent(this, LoginActivity::class.java)
            }

            startActivity(intent)
        }
    }
}