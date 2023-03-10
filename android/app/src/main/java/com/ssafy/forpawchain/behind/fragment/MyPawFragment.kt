package com.ssafy.forpawchain.behind.fragment

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.forpawchain.databinding.FragmentMyPawBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.adapter.SearchResultAdapter
import com.ssafy.forpawchain.viewmodel.fragment.MyPawFragmentVM


class MyPawFragment : Fragment() {
    private lateinit var viewModel: MyPawFragmentVM
    private var _binding: FragmentMyPawBinding? = null

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
        _binding = FragmentMyPawBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MyPawFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val recyclerView = binding.recycler
        val searchList = mutableListOf<MyPawListDTO>()

        searchList.add(MyPawListDTO("별", "여아", "개과", "말티즈", "O"))
        searchList.add(MyPawListDTO("뚱이", "여아", "개과", "비숑", "X"))

        recyclerView.adapter = MyPawListAdapter(searchList,
            onClickQrButton = {
//                viewModel.deleteTask(it)
            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.todoLiveData.observe(
            requireActivity(),
            Observer { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
                (binding.recycler.adapter as SearchResultAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

            })


        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}