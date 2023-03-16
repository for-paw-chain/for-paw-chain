package com.ssafy.forpawchain.behind.activity

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private lateinit var navController: NavController

    //    private lateinit var binding: ActivityMainBinding
    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)

//        binding = ActivityMainBinding.inflate(layoutInflater)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_house, R.id.navigation_house
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val actionBar: ActionBar? = supportActionBar
//        actionBar?.hide()
    }

    override fun onBackPressed() {
        // 뒤로가기 버튼 클릭
        Log.d(TAG, "뒤로가기")
        if (navController.backQueue.size <= 1) {
            // 2초내 다시 클릭하면 앱 종료
            if (System.currentTimeMillis() - backPressedTime < 2000) {
                android.os.Process.killProcess(android.os.Process.myPid());
                return
            }
            // 처음 클릭 메시지
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        } else {
            navController.navigateUp()
//            navController.popBackStack()
        }
    }
}