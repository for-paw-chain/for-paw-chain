package com.ssafy.forpawchain.behind.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.forpawchain.databinding.FragmentDiagnosisDetailBinding
import com.ssafy.forpawchain.model.domain.DianosisNewDTO
import com.ssafy.forpawchain.model.domain.HistoryDTO
import com.ssafy.forpawchain.viewmodel.adapter.DiagnosisNewRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.fragment.DiagnosisDetailFragmentVM

class DiagnosisDetailFragment : Fragment() {
    private var _binding: FragmentDiagnosisDetailBinding? = null
    private lateinit var viewModel: DiagnosisDetailFragmentVM
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
        _binding = FragmentDiagnosisDetailBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(DiagnosisDetailFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        val recyclerView = binding.recycler

        recyclerView.adapter = DiagnosisNewRecyclerViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        viewModel.todoLiveData.observe(
            requireActivity()
        ) { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
            (binding.recycler.adapter as DiagnosisNewRecyclerViewAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

        }


        val bundle = arguments
        if (bundle != null) {
            val value = bundle.getSerializable("item") as HistoryDTO
            viewModel.title.postValue(value.title)
            viewModel.body.postValue(value.body)
            viewModel.date.postValue(value.date)
            viewModel.name.postValue(value.writer)
            for (item in value.extra) {
                viewModel.addTask(
                    DianosisNewDTO(
                        MutableLiveData(item.title),
                        MutableLiveData(item.body)
                    )
                )
            }
            // Do something with value
        }



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearTask()
//        _binding = null
    }
}