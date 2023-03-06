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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollEvent()
    }

    private fun scrollEvent() {
//        binding.appBarLayout.overScrollMode = View.OVER_SCROLL_NEVER
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            //  Vertical offset == 0 indicates appBar is fully expanded.
//            Log.d(TAG, "${Math.abs(verticalOffset)}")
            if (Math.abs(verticalOffset) > 1300) {
                if (!isOpenSearch) {
                    Log.d(TAG, "열림")
                    // TODO: DUMMY DATA
                    viewModel.addTask(SearchResultDTO("별이1", "여아", "견과", "말티즈", "X"))
                    viewModel.addTask(SearchResultDTO("별이2", "여아", "견과", "말티즈", "O"))
                    viewModel.addTask(SearchResultDTO("별이3", "여아", "견과", "말티즈", "O"))
                    viewModel.addTask(SearchResultDTO("별이4", "여아", "견과", "말티즈", "O"))
                    viewModel.addTask(SearchResultDTO("별이5", "여아", "견과", "말티즈", "X"))
                    viewModel.addTask(SearchResultDTO("별이6", "여아", "견과", "말티즈", "X"))
                    isOpenSearch = true
                }
                invalidateOptionsMenu((activity as MainActivity))
            } else if (Math.abs(verticalOffset) < 100) {
                if (isOpenSearch) {
                    Log.d(TAG, "닫힘")
                    viewModel.clearTask()
                    isOpenSearch = false
                }
                invalidateOptionsMenu((activity as MainActivity))
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}