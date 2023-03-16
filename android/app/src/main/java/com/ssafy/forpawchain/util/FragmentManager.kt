package com.ssafy.forpawchain.util

import android.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.behind.fragment.PawFragment
import java.util.*


class FragmentManager {
    private var context: MainActivity? = null
    private var backKeyPressedTime: Long = 0

    fun setMainActivity(main: MainActivity?) {
        context = main
    }

    private val history_fragment: Stack<Fragment> = Stack()

    fun changeFragment(frag: Fragment?): Boolean? {
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 5초가 지나지 않았으면 종료
        return changeFragment(frag, true)
    }

    fun changeFragment(frag: Fragment?, flag: Boolean): Boolean? {
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간에 5초를 더해 현재 시간과 비교 후
        // 마지막으로 뒤로 가기 버튼을 눌렀던 시간이 5초가 지나지 않았으면 종료
//        if (System.currentTimeMillis() >= backKeyPressedTime + 5000 || flag) {
//
//            val transaction =  context?.supportFragmentManager?.beginTransaction()
//            transaction.replace(R.id.na, PawFragment())
//            transaction.addToBackStack(null)
//            transaction.commit()
//            return true
//        }
        return false
    }

}