package com.ssafy.forpawchain.behind.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.activity.LoginActivity
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.databinding.FragmentDoctorCertBinding
import com.ssafy.forpawchain.databinding.FragmentUserBinding
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.util.DoctorCertFragmentVMFactory
import com.ssafy.forpawchain.viewmodel.adapter.MyPageMenuAdapter
import com.ssafy.forpawchain.viewmodel.adapter.SearchResultAdapter
import com.ssafy.forpawchain.viewmodel.fragment.DoctorCertFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.HouseFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.UserFragmentVM

class DoctorCertFragment : Fragment() {
    private lateinit var viewModel: DoctorCertFragmentVM
    private var _binding: FragmentDoctorCertBinding? = null
    private lateinit var navController: NavController

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val TAG: String? = this::class.qualifiedName


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDoctorCertBinding.inflate(inflater, container, false)
        activity?.let {
            val viewModelFactory = DoctorCertFragmentVMFactory(requireContext())
            viewModel = ViewModelProvider(it, viewModelFactory).get(DoctorCertFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        initObserve()
        return root
    }

    private fun initObserve() {
        viewModel.openEvent.eventObserve(this) { obj ->

            when (obj) {
                ActivityCode.FRAGMENT_USER -> navController.navigate(R.id.navigation_user)
                else -> {
                    null
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}