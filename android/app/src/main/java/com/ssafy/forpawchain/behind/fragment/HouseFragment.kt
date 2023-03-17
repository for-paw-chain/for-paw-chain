package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.databinding.FragmentHouseBinding
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.viewmodel.adapter.SearchResultAdapter
import com.ssafy.forpawchain.viewmodel.fragment.HouseFragmentVM

class HouseFragment : Fragment() {
    private lateinit var viewModel: HouseFragmentVM
    private var _binding: FragmentHouseBinding? = null
    private lateinit var navController: NavController

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val TAG: String? = this::class.qualifiedName
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

        val recyclerView = binding.recycler
        val searchList = mutableListOf<SearchResultDTO>()

        recyclerView.adapter = SearchResultAdapter(searchList,
            onClickQrButton = {
                viewModel.deleteTask(it)
            },
            onClickDetailButton = {
                // detail
                // TODO(): navController
                val bundle = Bundle().apply {
                    putParcelable("search_result", it)
                }
                navController.navigate(R.id.navigation_search_result, bundle)
            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.todoLiveData.observe(
            requireActivity(),
            Observer { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
                (binding.recycler.adapter as SearchResultAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

            })

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    // 엔터키가 눌렸을 때 처리할 코드 작성

                    Log.d(TAG, "오 눌렸어~")
                    true
                }
                else -> false
            }
        }
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

        val root: View = binding.root
        initObserve()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        scrollEvent()
    }

    private fun scrollEvent() {
//        binding.appBarLayout.overScrollMode = View.OVER_SCROLL_NEVER
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            //  Vertical offset == 0 indicates appBar is fully expanded.
            //Log.d(TAG, "${verticalOffset}, ${appBarLayout.totalScrollRange}, ${appBarLayout.height}")
            if (-verticalOffset >= appBarLayout.totalScrollRange-1) {
                if (!viewModel.isOpenSearch.value!!) {
                    Log.d(TAG, "열림")
                    // TODO: DUMMY DATA
                    viewModel.addTask(SearchResultDTO("별이1", "여아", "견과", "말티즈", "X"))
                    viewModel.addTask(SearchResultDTO("별이2", "여아", "견과", "말티즈", "O"))
                    viewModel.addTask(SearchResultDTO("별이3", "여아", "견과", "말티즈", "O"))
                    viewModel.addTask(SearchResultDTO("별이4", "여아", "견과", "말티즈", "O"))
                    viewModel.addTask(SearchResultDTO("별이5", "여아", "견과", "말티즈", "X"))
                    viewModel.addTask(SearchResultDTO("별이6", "여아", "견과", "말티즈", "X"))
                    viewModel.isOpenSearch.value = true
                }
                invalidateOptionsMenu((activity as MainActivity))
            } else {
                if (viewModel.isOpenSearch.value!!) {
                    Log.d(TAG, "닫힘")
                    viewModel.clearTask()
                    viewModel.isOpenSearch.value = false
                }
                invalidateOptionsMenu((activity as MainActivity))
            }
        }
    }

    private fun initObserve() {
        viewModel.openEvent.eventObserve(this) { obj ->

            when (obj) {
                // TODO: navController
                ActivityCode.FRAGMENT_USER -> navController.navigate(R.id.navigation_user)
                else -> {
                    null
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}