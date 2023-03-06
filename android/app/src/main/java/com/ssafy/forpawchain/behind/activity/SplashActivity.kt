package com.ssafy.forpawchain.behind.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.forpawchain.R


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startLoading();
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            // Splash Screen이 뜨고 나서 실행될 Activity 연결
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }, 2000)
    }
}