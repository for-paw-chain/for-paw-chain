package com.ssafy.forpawchain.behind.fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.kakao.sdk.user.UserApiClient
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.activity.LoginActivity
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.behind.activity.SplashActivity
import com.ssafy.forpawchain.behind.dialog.WithdrawalDialog
import com.ssafy.forpawchain.databinding.FragmentUserBinding
import com.ssafy.forpawchain.model.domain.MyPageMenuDTO
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.model.service.UserService
import com.ssafy.forpawchain.util.PreferenceManager
import com.ssafy.forpawchain.viewmodel.adapter.MyPageMenuAdapter
import com.ssafy.forpawchain.viewmodel.fragment.UserFragmentVM
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response


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
        savedInstanceState: Bundle?,
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

        val preferenceManager = PreferenceManager()
        val token = preferenceManager.getString(requireContext(), "token")!!

        recyclerView.adapter = MyPageMenuAdapter(
            onClickEnterButton = {
                if (it.title.equals("의사 면허 등록")) {
                    // TODO: navController
                    navController.navigate(R.id.navigation_doctor_cert)
                } else if (it.title.equals("나의 반려 동물")) {
                    // TODO: navController
                    navController.navigate(R.id.navigation_my_paw_list)
                } else if (it.title.equals("회원 탈퇴")) {
                    // dialog는 확인 창
                    val dialog = WithdrawalDialog(requireContext(), object : IPermissionDelete {
                        override fun onDeleteBtnClick() {
                            CoroutineScope(Dispatchers.Main).launch {
                                val response = withContext(Dispatchers.IO) {
                                    UserService().signOutUser(token).enqueue(object :
                                        Callback<JsonObject> {
                                        override fun onResponse(
                                            call: Call<JsonObject>,
                                            response: Response<JsonObject>
                                        ) {
                                            if (response.isSuccessful) {
                                                // 정상적으로 통신이 성공된 경우
                                                lifecycleScope.launch {

                                                    // 카카오 로그아웃 부분
                                                    UserApiClient.instance.logout { error ->
                                                        if (error != null) {
                                                            // 에러가 발생한 경우 처리합니다.
                                                            Log.d(TAG, " 카카오 로그아웃 에러 발생")
                                                        } else {
                                                            // 로그아웃이 성공한 경우 처리합니다.
                                                            Log.d(TAG, " 카카오 로그아웃 성공")
                                                        }
                                                    }

                                                    // unlink는 카카오 회원탈퇴
                                                    UserApiClient.instance.unlink { error ->
                                                        if (error != null) {
                                                            Log.d(UserFragment.TAG, "카카오 회원 탈퇴 에러 발생")
                                                        } else {
                                                            Log.d(UserFragment.TAG, "카카오 회원 탈퇴")
                                                        }
                                                    }

                                                    preferenceManager.clear(context!!)
                                                    requireActivity().finish()
                                                    startActivity(Intent(context, SplashActivity::class.java))

                                                    Log.d(TAG, "회원 탈퇴 성공 " + response);
                                                    Log.d(TAG, "회원 탈퇴 성공 " + response.body());
                                                    Toast.makeText(requireContext(), "회원 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show()
                                                }
                                            } else {
                                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                                Log.d(TAG, "회원 탈퇴 실패 " + response)
                                                Log.d(TAG, "회원 탈퇴 실패 " + response.body());
                                            }
                                        }
                                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                            Log.d(TAG, "회원 탈퇴 onFailure 에러: " + t.message.toString());
                                        }
                                    })
                                }
                            }
                        }
                    })
                    dialog.show()
                } else if (it.title.equals("로그아웃")) {
                    Log.d(TAG, "로그아웃 누름")

                    GlobalScope.launch {
                        val response = withContext(Dispatchers.IO) {
                            UserService().logoutUser(token).enqueue(object :
                                Callback<JsonObject> {
                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성공된 경우
                                        lifecycleScope.launch {
                                            requireActivity().finish()
                                            startActivity(Intent(context, LoginActivity::class.java))
                                        }
                                        // call
                                        Log.d(TAG, "로그 아웃 성공 " + response);

                                    } else {
                                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                        Log.d(TAG, "로그 아웃 실패 " + response)
                                    }
                                }
                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                                    Log.d(SplashActivity.TAG, "onFailure 에러: " + t.message.toString());
                                }
                            })
                        }
                    }


//                    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//                        if (error != null) {
//                            // 액세스 토큰 정보를 가져오는 중 에러가 발생한 경우 처리합니다.
//                            Log.d(UserFragment.TAG, "액세스 토큰 정보를 가져오는 중 에러가 발생")
//                            requireActivity().finish()
//                            var intent: Intent? = Intent(requireContext(), LoginActivity::class.java)
//                            startActivity(intent)
//                        } else if (tokenInfo != null) {
//                            // 액세스 토큰 정보를 가져왔고, 로그인한 사용자인 경우 로그아웃을 수행합니다.
////                            UserApiClient.instance.logout { error ->
////                                if (error != null) {
////                                    // 에러가 발생한 경우 처리합니다.
////                                    Log.d(UserFragment.TAG, "에러 발생")
////                                } else {
////                                    // 로그아웃이 성공한 경우 처리합니다.
////                                    Log.d(UserFragment.TAG, "정상적인 로그아웃")
////                                    requireActivity().finish()
////                                    var intent: Intent? = Intent(requireContext(), LoginActivity::class.java)
////                                    startActivity(intent)
//////                                    dialog = new ProgressDialog(this);
//////                                    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//////                                    //dialog.setIndeterminate(true);
//////                                    dialog.setCancelable(true);
//////                                    //dialog.setCancelable(false); ProcessDialog를 취소할 수 있는지를 결정한다.
//////                                    // 디폴트는 true인듯하다
//////                                    dialog.setMessage("Checking Data");
//////                                    dialog.show();
////                                }
////                            }
//                        } else {
//                            // 로그인하지 않은 사용자인 경우 처리합니다.
//                            Log.d(UserFragment.TAG, "로그인하지 않은 사용자의 로그아웃")
//                        }
//                    }

                } else if (it.title.equals("내가 쓴 글")) {
                    navController.navigate(R.id.navigation_my_paw_history)
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

//        viewModel.addTask(
//            MyPageMenuDTO(
//                resources.getDrawable(R.drawable.icon_android_emoji),
//                "버전 확인"
//            )
//        )

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