package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.dialog.QRCreateDialog
import com.ssafy.forpawchain.behind.dialog.WithdrawalAnimalDialog
import com.ssafy.forpawchain.databinding.FragmentMyPawBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.model.interfaces.IWithdrawalAnimal
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.service.AuthService
import com.ssafy.forpawchain.util.ImageSave
import com.ssafy.forpawchain.util.PreferenceManager
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.fragment.MyPawFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            viewModel = ViewModelProvider(this).get(MyPawFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val recyclerView = binding.recycler
        val searchList = mutableListOf<MyPawListDTO>()

        recyclerView.adapter = MyPawListAdapter(searchList,
            {
                // qr

                it.code.value?.let { it1 ->
                    QRCreateDialog(requireContext(), it1) {
                        ImageSave().saveImageToGallery(
                            requireContext(),
                            it,
                            "qrImage",
                            "Created QR in PawForChain"
                        )
                    }
                }?.show()
//                viewModel.deleteTask(it)
            }, {
                // del
                val dialog = WithdrawalAnimalDialog(it, requireContext(), object : IWithdrawalAnimal {
                    override fun onDeleteBtnClick(pid: String, myPawListDTO: MyPawListDTO) {
                        GlobalScope.launch {
                            val token = PreferenceManager().getString(requireContext(), "token")!!
                            val response = withContext(Dispatchers.IO) {
                                AuthService().removePetAuth(
                                    UserInfo.uid.toInt(),
                                    pid.substring(1),
                                    token
                                ).enqueue(object :
                                    Callback<JsonObject> {
                                    override fun onResponse(
                                        call: Call<JsonObject>,
                                        response: Response<JsonObject>
                                    ) {
                                        if (response.isSuccessful) {
                                            // 정상적으로 통신이 성공된 경우
                                            lifecycleScope.launch {
                                            }
                                            // call
                                            Log.d(TAG, "onResponse 성공");

                                        } else {
                                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                            Log.d(TAG, "onResponse 실패")
                                        }
                                    }

                                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                        Log.d(TAG, "onFailure 에러: " + t.message.toString());
                                    }
                                })
                            }
                        }
                        Log.d(TAG, "반려동물 삭제")
                    }
                })
                dialog.show()
//                viewModel.deleteTask(it)
            }, {
                // detail
                val bundle = Bundle()
                bundle.putSerializable("item", it)
                navController.navigate(R.id.navigation_permission_paw, bundle)

            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

//        viewModel.addTask(MyPawListDTO("별", "여아", "개과", "말티즈", "O"))
//        viewModel.addTask(MyPawListDTO("뚱이", "여아", "개과", "비숑", "X"))
        lifecycleScope.launch {
            viewModel.initData()
        }
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
