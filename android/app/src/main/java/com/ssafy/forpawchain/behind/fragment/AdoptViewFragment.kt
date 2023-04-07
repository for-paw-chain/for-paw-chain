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
import androidx.room.Room
import com.google.gson.JsonObject
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.databinding.FragmentAdoptViewBinding
import com.ssafy.forpawchain.model.domain.User
import com.ssafy.forpawchain.model.room.AppDatabase
import com.ssafy.forpawchain.model.room.UserDao
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.util.ImageLoader
import com.ssafy.forpawchain.util.PreferenceManager
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
        var code = ""

        val token =  PreferenceManager().getString(requireContext(), "token")!!

        lifecycleScope.launch {
            bundle?.getString("pid")?.let {
                viewModel.initInfo(it)
                code = it.toString()
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

            // 새로운 안드로이드 스튜디오에서 개발 할 때마다 insert로 Room을 등록해야함, 자세히 버튼을 누르면 다운 됨
            // 디버그 실행하고 다시 주석처리 하기
//            userDao.insert(User("private", "6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544"))

            val user = userDao.getUserById("private")
            // TODO: 수정 필요
//            user.privateKey = "6169940ca8cb18384b5000199566c387da4f8d9caed51ffe7921b93c488d2544"
            if (user != null) {
                lifecycleScope.launch {
                    bundle?.getString("pid")?.let {
                        AdoptService().getCA(it, token)
                            .enqueue(object :
                                Callback<JsonObject> {
                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성공된 경우
                                        var ca = response.body()?.get("content").toString()
                                        Log.d(TAG, "컨트랙트 주소 : " + ca)
                                        ca = ca.replace("\"", "")
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
            else {
                lifecycleScope.launch {
                    bundle?.getString("pid")?.let {
                        AdoptService().getCA(it, token)
                            .enqueue(object :
                                Callback<JsonObject> {
                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성공된 경우
                                        var ca = response.body()?.get("content").toString()
                                        Log.d(TAG, "컨트랙트 주소 : " + ca)
                                        ca = ca.replace("\"", "")
                                        ForPawChain.setBlockChain(
                                            ca,
                                            "faee15c534f72212de7f83070c68bade01071d0ca6256a761ea568cbcf832714"
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
        // 공고 추가
        binding.fab.setOnClickListener { view ->
            var bundle = Bundle()
            bundle.putString("code", code)
            navController.navigate(R.id.navigation_adopt_create, bundle)
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
//        _binding = null
    }
}
