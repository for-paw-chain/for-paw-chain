package com.ssafy.forpawchain.behind.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.activity.LoginActivity
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.behind.dialog.WithdrawalDialog
import com.ssafy.forpawchain.databinding.FragmentUserBinding
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.viewmodel.adapter.MyPageMenuAdapter
import com.ssafy.forpawchain.viewmodel.fragment.UserFragmentVM

class UserFragment : Fragment() {
    private lateinit var viewModel: UserFragmentVM
    private var _binding: FragmentUserBinding? = null
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
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(UserFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        val recyclerView = binding.recycler
        val searchList = mutableListOf<MyPageMenuDTO>()

        recyclerView.adapter = MyPageMenuAdapter(
            onClickEnterButton = {
                if (it.title.equals("의사 면허 등록")) {
                    // TODO: navController
                    navController.navigate(R.id.navigation_doctor_cert)

                } else if (it.title.equals("나의 반려 동물")) {
                    // TODO: navController
                    navController.navigate(R.id.navigation_my_paw_list)
                } else if (it.title.equals("회원 탈퇴")) {
                    val dialog = WithdrawalDialog(requireContext(), object : IPermissionDelete {
                        override fun onDeleteBtnClick() {
                            Log.d(TAG, "회원탈퇴 완료")
                        }
                    })

                    dialog.show()
                } else if (it.title.equals("로그아웃")) {
                    requireActivity().finish()
                    var intent: Intent? = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                }
//                viewModel.deleteTask(it)
            })
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.todoLiveData.observe(
            requireActivity(),
            Observer { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
                (binding.recycler.adapter as MyPageMenuAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

            })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(requireView())

        viewModel.addTask(
            MyPageMenuDTO(
                resources.getDrawable(R.drawable.icon_dog_emoji),
                "나의 반려 동물"
            )
        )

        viewModel.addTask(
            MyPageMenuDTO(
                resources.getDrawable(R.drawable.icon_doctor_emoji),
                "의사 면허 등록"
            )
        )

        viewModel.addTask(
            MyPageMenuDTO(
                resources.getDrawable(R.drawable.icon_handshake_emoji),
                "내가 쓴 글"
            )
        )

        viewModel.addTask(
            MyPageMenuDTO(
                resources.getDrawable(R.drawable.icon_android_emoji),
                "버전 확인"
            )
        )

        viewModel.addTask(
            MyPageMenuDTO(
                resources.getDrawable(R.drawable.icon_broken_heart_emoji),
                "회원 탈퇴"
            )
        )

        viewModel.addTask(
            MyPageMenuDTO(
                resources.getDrawable(R.drawable.icon_logout_emoji),
                "로그아웃"
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearTask()
//        _binding = null // TODO: 왜 이이거 하면 다시 페이지를 로딩 했을 떄 에러가 나는지 의문
    }
}