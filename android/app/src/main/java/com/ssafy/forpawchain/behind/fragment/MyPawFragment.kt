package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.dialog.WithdrawalAnimalDialog
import com.ssafy.forpawchain.behind.dialog.WithdrawalDialog
import com.ssafy.forpawchain.databinding.FragmentMyPawBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.adapter.SearchResultAdapter
import com.ssafy.forpawchain.viewmodel.fragment.MyPawFragmentVM


class MyPawFragment : Fragment() {
    private lateinit var viewModel: MyPawFragmentVM
    private var _binding: FragmentMyPawBinding? = null
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
        _binding = FragmentMyPawBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MyPawFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val recyclerView = binding.recycler
        val searchList = mutableListOf<MyPawListDTO>()

        recyclerView.adapter = MyPawListAdapter(searchList,
            {
                // qr
//                viewModel.deleteTask(it)
            }, {
                // del
                val dialog = WithdrawalAnimalDialog(requireContext(), object : IPermissionDelete {
                    override fun onDeleteBtnClick() {
                        viewModel.deleteTask(it)
                        Log.d(TAG, "반려동물 삭제 완료")
                    }
                })

                dialog.show()
//                viewModel.deleteTask(it)
            }, {
                // detail
                // TODO: navController
                navController.navigate(R.id.navigation_permission_paw)

            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.addTask(MyPawListDTO("별", "여아", "개과", "말티즈", "O"))
        viewModel.addTask(MyPawListDTO("뚱이", "여아", "개과", "비숑", "X"))

        viewModel.todoLiveData.observe(
            requireActivity(),
            Observer { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
                (binding.recycler.adapter as MyPawListAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

            })


        val root: View = binding.root
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