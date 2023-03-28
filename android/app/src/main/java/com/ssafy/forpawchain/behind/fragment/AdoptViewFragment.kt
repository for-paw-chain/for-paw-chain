package com.ssafy.forpawchain.behind.fragment

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.databinding.FragmentAdoptViewBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.room.AppDatabase
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.ImageLoader
import com.ssafy.forpawchain.viewmodel.adapter.DiagnosisRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.fragment.AdoptViewFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.MyPawFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.PawFragmentVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdoptViewFragment : Fragment() {
    private var _binding: FragmentAdoptViewBinding? = null
    private lateinit var viewModel: AdoptViewFragmentVM
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

        recyclerView.adapter = DiagnosisRecyclerViewAdapter {
            val bundle = Bundle()
            bundle.putSerializable("item", it)
            navController.navigate(R.id.navigation_diagnosis_detail, bundle)
            Log.d(TAG, "의료기록 상세 조회")
        }


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)
        viewModel.todoLiveData.observe(
            requireActivity()
        ) { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
            (binding.recycler.adapter as DiagnosisRecyclerViewAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

        }
        CoroutineScope(Dispatchers.IO).launch {
            val db = Room.databaseBuilder(
                requireContext(),
                AppDatabase::class.java, "database-name"
            ).build()
            val userDao = db.userDao()
            val user = userDao.getUserById("private")
            if (user != null) {
                lifecycleScope.launch {
                    bundle?.getString("pid")?.let {
                        AdoptService().getCA(it)
                            .enqueue(object :
                                Callback<JsonObject> {
                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성공된 경우
                                        val ca = response.body()?.get("content").toString()

                                        ForPawChain.setBlockChain(
                                            ca,
                                            user.privateKey
                                        )
                                        val history = ForPawChain.getHistory()
                                        for (item in history) {
                                            viewModel.addTask(item)
                                        }
                                        Log.d(TAG, "onResponse 성공");
                                    } else {
                                        Log.d(TAG, "onResponse 실패");

                                    }
                                }

                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    Log.d(TAG, "네트워크 에러");

                                }
                            })
                    }


                }
            }
        }
        binding.fab.setOnClickListener { view ->
            navController.navigate(R.id.navigation_adopt_create)
            Log.d(MyPawHistoryFragment.TAG, "공고 추가")
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