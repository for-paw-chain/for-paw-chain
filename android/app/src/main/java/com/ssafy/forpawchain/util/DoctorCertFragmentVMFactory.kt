package com.ssafy.forpawchain.util

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ssafy.forpawchain.viewmodel.fragment.DoctorCertFragmentVM

class DoctorCertFragmentVMFactory(private val application: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorCertFragmentVM::class.java)) {
            return DoctorCertFragmentVM(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
