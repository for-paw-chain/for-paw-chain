package com.ssafy.forpawchain.behind.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.dialog.AdoptCRUDDialog
import com.ssafy.forpawchain.behind.dialog.PermissionDialog
import com.ssafy.forpawchain.databinding.FragmentMyPawHistoryBinding
import com.ssafy.forpawchain.databinding.FragmentPawBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IAdoptCRUD
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.viewmodel.adapter.AdoptRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.fragment.MyPawHistoryFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.PawFragmentVM
import kotlinx.coroutines.launch

class MyPawHistoryFragment : Fragment() {
    private var _binding: FragmentMyPawHistoryBinding? = null
    private lateinit var viewModel: MyPawHistoryFragmentVM
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
        _binding = FragmentMyPawHistoryBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(MyPawHistoryFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root

        val recyclerView = binding.recycler

        recyclerView.adapter = AdoptRecyclerViewAdapter(
            {
                // TODO: Detail
                navController.navigate(R.id.navigation_adopt_view)
                Log.d(TAG, "입분양 디테일 뷰")
            },
            {
                // TODO: CRUD
//                navController.navigate(R.id.navigation_permission_paw)
                val dialog = AdoptCRUDDialog(requireContext(), object : IAdoptCRUD {
                    override fun onUpdateBtnClick() {
                        // 공고 수정으로 이동
                        Log.d(TAG, "공고 수정으로")
                        navController.navigate(R.id.navigation_adopt_update)
                    }

                    override fun onDeleteBtnClick() {
                        // 공고 삭제
                        viewModel.deleteTask(it)
                        Log.d(TAG, "공고 삭제")
                    }

                })

                dialog.show()
            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        lifecycleScope.launch {
            viewModel.initData()
        }

        viewModel.todoLiveData.observe(
            requireActivity()
        ) { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
            (binding.recycler.adapter as AdoptRecyclerViewAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

        }

        binding.fab.setOnClickListener { view ->
            // TODO: 공고 추가
            navController.navigate(R.id.navigation_adopt_add)
            Log.d(TAG, "공고 추가")
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
        // _binding = null
    }
}