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
import com.google.gson.JsonObject
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.dialog.AdoptCRUDDialog
import com.ssafy.forpawchain.behind.dialog.PermissionDialog
import com.ssafy.forpawchain.databinding.FragmentPawBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IAdoptCRUD
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.viewmodel.adapter.AdoptRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.fragment.PawFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PawFragment : Fragment() {
    private var _binding: FragmentPawBinding? = null
    private lateinit var viewModel: PawFragmentVM
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
        _binding = FragmentPawBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(PawFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root

        val recyclerView = binding.recycler

        recyclerView.adapter = AdoptRecyclerViewAdapter(
            {
                val bundle = Bundle()
                bundle.putString("pid", it.pid)
                navController.navigate(R.id.navigation_adopt_view, bundle)
                Log.d(TAG, "입분양 디테일 뷰")
            },
            {
//                navController.navigate(R.id.navigation_permission_paw)
                val dialog = AdoptCRUDDialog(requireContext(), object : IAdoptCRUD {
                    override fun onUpdateBtnClick() {
                        // 공고 수정으로 이동
                        Log.d(TAG, "공고 수정으로")
                        navController.navigate(R.id.navigation_adopt_update)
                    }

                    override fun onDeleteBtnClick() {
                        // 공고 삭제
                        lifecycleScope.launch() {
                            val response = withContext(Dispatchers.IO) {
                                AdoptService().deleteAdopt(it.pid).enqueue(object :
                                    Callback<ResponseBody> {
                                    override fun onResponse(
                                        call: Call<ResponseBody>,
                                        response: Response<ResponseBody>
                                    ) {
                                        if (response.isSuccessful) {
                                            viewModel.deleteTask(it)
                                        } else {

                                        }
                                    }

                                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                        Log.d(TAG, "dd")
                                    }

                                })
                            }
                        }

//                        viewModel.deleteTask(it)
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