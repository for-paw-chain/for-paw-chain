package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.databinding.FragmentHouseBinding
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.viewmodel.adapter.SearchResultAdapter
import com.ssafy.forpawchain.viewmodel.fragment.HouseFragmentVM


class HouseFragment : Fragment() {
    private lateinit var viewModel: HouseFragmentVM
    private var _binding: FragmentHouseBinding? = null
    private var isOpenSearch: Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val TAG: String = "HouseFragment"
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHouseBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(HouseFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }
        Thread {
            binding.searchEditText.onFocusChangeListener =
                OnFocusChangeListener { v, gainFocus ->
                    //포커스가 주어졌을 때 동작
                    if (gainFocus) {
                        //to do
                        //원하는 동작
                        Log.d(TAG, "포커스 온")
                    } else {
                        //원하는 동작
                        Log.d(TAG, "포커스 아웃")
                    }
                }
        }

        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollEvent()
    }

    private fun scrollEvent() {
//        binding.appBarLayout.overScrollMode = View.OVER_SCROLL_NEVER
        binding.appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            //  Vertical offset == 0 indicates appBar is fully expanded.
//            Log.d(TAG, "${Math.abs(verticalOffset)}")
            if (Math.abs(verticalOffset) > 1300) {
                if (!isOpenSearch) {
                    Log.d(TAG, "열림")
                    val recyclerView = binding.recycler
                    val searchList = mutableListOf<SearchResultDTO>()
                    searchList.add(SearchResultDTO("별이1", "여아", "견과", "말티즈", "O"))
                    searchList.add(SearchResultDTO("별이2", "여아", "견과", "말티즈", "X"))
                    searchList.add(SearchResultDTO("별이3", "여아", "견과", "말티즈", "O"))
                    searchList.add(SearchResultDTO("별이4", "여아", "견과", "말티즈", "X"))
                    searchList.add(SearchResultDTO("별이5", "여아", "견과", "말티즈", "X"))
                    searchList.add(SearchResultDTO("별이6", "여아", "견과", "말티즈", "X"))

                    val newsAdapter = SearchResultAdapter(searchList)

                    recyclerView.adapter = newsAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.setHasFixedSize(true)
                    isOpenSearch = true
                }
                invalidateOptionsMenu((activity as MainActivity))
            } else if (Math.abs(verticalOffset) < 100) {
                if (isOpenSearch) {
                    val recyclerView = binding.recycler
                    val newsAdapter = SearchResultAdapter(emptyList())
                    recyclerView.adapter = newsAdapter;
                    Log.d(TAG, "닫힘")
                    isOpenSearch = false
                }
                invalidateOptionsMenu((activity as MainActivity))
            }
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}