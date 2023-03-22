package com.ssafy.forpawchain.behind.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.forpawchain.databinding.FragmentAdoptViewBinding
import com.ssafy.forpawchain.model.domain.DiagnosisHistoryDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.viewmodel.adapter.AdoptRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.adapter.DiagnosisRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.fragment.AdoptViewFragmentVM
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class AdoptViewFragment : Fragment() {
    private var _binding: FragmentAdoptViewBinding? = null
    private lateinit var viewModel: AdoptViewFragmentVM

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
        _binding = FragmentAdoptViewBinding.inflate(inflater, container, false)



        activity?.let {
            viewModel = ViewModelProvider(it).get(AdoptViewFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root

        val bundle = arguments

        lifecycleScope.launch {
            bundle?.getString("pid")?.let {
                viewModel.initInfo(it)
                binding.item = viewModel.pawInfo
            }
        }

        val recyclerView = binding.recycler

        recyclerView.adapter = DiagnosisRecyclerViewAdapter(
            {
                // TODO: 의료기록 상세 보기로 넘어가야함.
                Log.d(TAG, "의료기록 상세 조회")
            })


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        viewModel.todoLiveData.observe(
            requireActivity()
        ) { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
            (binding.recycler.adapter as DiagnosisRecyclerViewAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

        }
        viewModel.addTask(DiagnosisHistoryDTO("[초진] 상담", "2022-03-03 오후 03:05:27", "Sign by 김의사"))
        viewModel.addTask(
            DiagnosisHistoryDTO(
                "[초진] 치과수술 / 담석 체크 / 좌측 pm4 발치",
                "2022-03-05 오후 04:05:27",
                "Sign by 김의사"
            )
        )
        viewModel.addTask(
            DiagnosisHistoryDTO(
                "[초진] 치과수술 / 담석 체크 / 좌측 pm4 발치",
                "2022-03-05 오후 04:05:27",
                "Sign by 김의사"
            )
        )
        viewModel.addTask(
            DiagnosisHistoryDTO(
                "[초진] 치과수술 / 담석 체크 / 좌측 pm4 발치",
                "2022-03-05 오후 04:05:27",
                "Sign by 김의사"
            )
        )
        viewModel.addTask(
            DiagnosisHistoryDTO(
                "[초진] 치과수술 / 담석 체크 / 좌측 pm4 발치",
                "2022-03-05 오후 04:05:27",
                "Sign by 김의사"
            )
        )

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearTask()
//        _binding = null
    }
}