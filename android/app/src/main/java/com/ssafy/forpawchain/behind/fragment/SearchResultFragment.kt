package com.ssafy.forpawchain.behind.fragment

import android.R
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.databinding.FragmentDoctorCertBinding
import com.ssafy.forpawchain.databinding.FragmentSearchResultBinding
import com.ssafy.forpawchain.viewmodel.fragment.DoctorCertFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.SearchResultFragmentVM

class SearchResultFragment : Fragment() {
    private lateinit var viewModel: SearchResultFragmentVM
    private var _binding: FragmentSearchResultBinding? = null
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
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SearchResultFragmentVM::class.java)
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
                // TODO: navController
                ActivityCode.FRAGMENT_USER -> navController.navigate(com.ssafy.forpawchain.R.id.navigation_user)
                else -> {
                    null
                }
            }

        }
    }
}