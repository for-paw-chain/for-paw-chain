package com.ssafy.forpawchain.behind.fragment

import android.R
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.databinding.FragmentSearchResultBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.viewmodel.fragment.AdoptViewFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.SearchResultFragmentVM

class SearchResultFragment : Fragment() {
    private lateinit var viewModel: SearchResultFragmentVM
    private lateinit var adoptviewModel : AdoptDTO
    private var _binding: FragmentSearchResultBinding? = null
    private lateinit var navController: NavController

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val TAG: String? = this::class.qualifiedName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(SearchResultFragmentVM::class.java)
            adoptviewModel =AdoptDTO(MutableLiveData(""), MutableLiveData(null), MutableLiveData(""), MutableLiveData(""), MutableLiveData(""))

            binding.viewModel = viewModel
            binding.adoptItem = adoptviewModel
            binding.lifecycleOwner = this
        }

        // HouseFragment에서 받아온 bundle의 데이터를 가져오는 부분
        val bundle = arguments

        bundle?.getParcelable<SearchResultDTO>("SearchResultItem")?.let {
            binding.searchResultItem = it
        }

        initObserve()
    }

    private fun initObserve() {
        viewModel.selectedSearchResult.observe(viewLifecycleOwner, Observer {
            binding.searchResultItem = it
            println("왜 안나와" + binding.searchResultItem)
        })
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
