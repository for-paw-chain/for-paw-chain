package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.behind.activity.SplashActivity
import com.ssafy.forpawchain.behind.dialog.QRCreateDialog
import com.ssafy.forpawchain.behind.dialog.WithdrawalAnimalDialog
import com.ssafy.forpawchain.databinding.FragmentHouseBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.model.service.PetService
import com.ssafy.forpawchain.model.service.UserService
import com.ssafy.forpawchain.util.ImageLoader
import com.ssafy.forpawchain.util.ImageSave
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.adapter.SearchResultAdapter
import com.ssafy.forpawchain.viewmodel.fragment.HouseFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.MyPawFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.PawFragmentVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HouseFragment : Fragment() {
    private lateinit var viewModel: HouseFragmentVM
    private var _binding: FragmentHouseBinding? = null
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
    ): View ? {
        _binding = FragmentHouseBinding.inflate(inflater, container, false)

        activity?.let {
            viewModel = ViewModelProvider(it).get(HouseFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        initObserve()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireView())
        scrollEvent()

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
//                val dialog = WithdrawalAnimalDialog(requireContext(), object : IPermissionDelete {
//                    override fun onDeleteBtnClick() {
//                        viewModel.deleteTask(it)
//                        Log.d(MyPawFragment.TAG, "반려동물 삭제 완료")
//                    }
//                })
//
//                dialog.show()
//                viewModel.deleteTask(it)
            },{
                // detail
                val bundle = Bundle()
                // MyPawListDTO를 > SearchResultDTO로 변경
                val searchResultDTO = SearchResultDTO(
                    code = it.code.value!!,
                    profile = it.profile?.value,
                    name = it.name.value!!,
                    sex = it.sex.value!!,
                    species = it.species.value!!,
                    kind = it.kind.value!!,
                    neutered = it.neutered.value!!,
                    birth = it.neutered.value,
                    region = it.neutered.value,
                    tel = it.neutered.value,
                    etc = it.neutered.value
                )
                bundle.putSerializable("searchResultVM", searchResultDTO)
                navController.navigate(R.id.navigation_search_result, bundle)
            },
        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.todoLiveData.observe(
            requireActivity(),
            Observer { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
                (binding.recycler.adapter as MyPawListAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.

            })

        //동물 등록 검색 번호 창 번호 입력 버튼
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            var input = binding.searchEditText.text.toString();
            var bundle = Bundle()
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    // 엔터키가 눌렸을 때 처리할 코드 작성
                    GlobalScope.launch {
                        response = getPetInfo(input)
                        when (response.code) {
                            "200" -> {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        requireContext(),
                                        "있는 동물입니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
//                                bundle.putSerializable("code", binding.searchEditText.text.toString())
//                                navController.navigate(R.id.navigation_search_result, bundle)
                            }
                            "206" -> {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        requireContext(),
                                        "있는 동물입니다22",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
//                                bundle.putSerializable("code", binding.searchEditText.text.toString())
//                                navController.navigate(R.id.navigation_search_result, bundle)
                            }
                            else -> {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        requireContext(),
                                        "없는 동물입니다",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
                    true
                }
                else -> false
            }
        }

        binding.searchEditText.onFocusChangeListener =
            OnFocusChangeListener { v, gainFocus ->
                //포커스가 주어졌을 때 동작
                if (gainFocus) {
                    //to do
                    //원하는 동작
                    Log.d(TAG, "포커스 온")
                } else {
                    //원하는 동작
                    Log.d(TAG, "포커스 아웃")
                }

            }
    }

    private fun scrollEvent() {
//        binding.appBarLayout.overScrollMode = View.OVER_SCROLL_NEVER
        binding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            //  Vertical offset == 0 indicates appBar is fully expanded.
            //Log.d(TAG, "${verticalOffset}, ${appBarLayout.totalScrollRange}, ${appBarLayout.height}")
            if (-verticalOffset >= appBarLayout.totalScrollRange-1) {
                if (!viewModel.isOpenSearch.value!!) {
                    Log.d(TAG, "열림")
                    // TODO: DUMMY DATA
//                    viewModel.addTask(SearchResultDTO("별이1", "여아", "견과", "말티즈", "X"))
//                    viewModel.addTask(SearchResultDTO("별이2", "여아", "견과", "말티즈", "O"))
//                    viewModel.addTask(SearchResultDTO("별이3", "여아", "견과", "말티즈", "O"))
//                    viewModel.addTask(SearchResultDTO("별이4", "여아", "견과", "말티즈", "O"))
//                    viewModel.addTask(SearchResultDTO("별이5", "여아", "견과", "말티즈", "X"))
//                    viewModel.addTask(SearchResultDTO("별이6", "여아", "견과", "말티즈", "X"))

                    /////

                    PetService().getMyPets().enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 성고된 경우
                                var result: JsonObject? = response.body()
                                val items = result?.get("content") as JsonArray
                                if (result != null) {
                                    for (item in items.asJsonArray) {
                                        var item1 = item as JsonObject
                                        if (item1["profile"].toString() == "null") {
                                            viewModel.addTask(MyPawListDTO(
                                                MutableLiveData<String>(item1["pid"].asString),
                                                null,
                                                MutableLiveData<String>(item1["name"].asString),
                                                MutableLiveData<String>(if (item1["sex"].asString.equals("MALE")) "남아" else "여아"),
                                                MutableLiveData<String>(if (item1["type"].asString.equals("DOG")) "개과" else if (item1["type"].asString.equals("CAT")) "고양이과" else "기타"),
                                                MutableLiveData<String>(item1["kind"].asString),
                                                MutableLiveData<String>(if (item1["spayed"].asString.equals("false")) "Ⅹ" else "○"),
                                            ))
                                        } else {
                                            ImageLoader().loadDrawableFromUrl(item1["profile"].asString) { drawable ->
                                                viewModel.addTask(
                                                    MyPawListDTO(
                                                        MutableLiveData<String>(item1["pid"].asString),
                                                        MutableLiveData<Drawable>(drawable),
                                                        MutableLiveData<String>(item1["name"].asString),
                                                        MutableLiveData<String>(if (item1["sex"].asString.equals("MALE")) "남아" else "여아"),
                                                        MutableLiveData<String>(if (item1["type"].asString.equals("DOG")) "개과" else if (item1["type"].asString.equals("CAT")) "고양이과" else "기타"),
                                                        MutableLiveData<String>(item1["kind"].asString),
                                                        MutableLiveData<String>(if (item1["spayed"].asString.equals("false")) "Ⅹ" else "○"),
                                                    )
                                                )
                                            }
                                        }
                                    }

                                }
                                Log.d(PawFragmentVM.TAG, "onResponse 성공: $result");
                            } else {
                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                                Log.d(MyPawFragmentVM.TAG, "onResponse 실패")
                            }
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                            Log.d(MyPawFragmentVM.TAG, "onFailure 에러: " + t.message.toString());
                        }
                    })

                    /////
                    viewModel.isOpenSearch.value = true
                }
                invalidateOptionsMenu((activity as MainActivity))
            } else {
                if (viewModel.isOpenSearch.value!!) {
                    Log.d(TAG, "닫힘")
                    viewModel.clearTask()
                    viewModel.isOpenSearch.value = false
                }
                invalidateOptionsMenu((activity as MainActivity))
            }
        }
    }

    private fun initObserve() {
        viewModel.openEvent.eventObserve(this) { obj ->

            when (obj) {
                ActivityCode.FRAGMENT_USER -> navController.navigate(R.id.navigation_user)
                else -> {
                    null
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    fun getPetInfo(input : String){
//        GlobalScope.launch {
//            val response = withContext(Dispatchers.IO) {
//                PetService().getPetInfo(input).enqueue(object :
//                    Callback<JsonObject> {
//                    override fun onResponse(
//                        call: Call<JsonObject>,
//                        response: Response<JsonObject>
//                    ) {
//                        if (response.isSuccessful) {
//                            // 정상적으로 통신이 성공된 경우
//                            var bundle = Bundle()
//                            when{
//                                //200 견적사항까지 있는 경우
//                                response.code().toString() == "200" ->{
//                                    Log.d(TAG, "검색바 응답 200" + response)
//                                    return response.code().toString()
//                                }
//
//                                //206 견적사항이 없는 경우
//                                response.code().toString() == "206" ->{
//                                    Log.d(TAG, "검색바 응답 206" + response)
//                                    return response.code().toString()
//                                }
//
//                                //500 없는 번호
//                                response.code().toString() == "500" ->{
//                                    Log.d(TAG, "검색바 응답 500" + response)
//                                    return response.code().toString()
//                                }
//                                else -> {
//                                    Log.d(TAG, "기타 에러" + response)
//                                    return false
//                                }
//                            }
//                        } else {
//                            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
//                            Log.d(UserFragment.TAG, "검색 실패 " + response)
//                        }
//                    }
//                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                        // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
//                        Log.d(SplashActivity.TAG, "onFailure 에러: " + t.message.toString());
//                    }
//                })
//            }
//        }
//    }

    suspend fun getPetInfo(input: String): String = withContext(Dispatchers.IO) {
        val response = PetService().getPetInfo(input).execute()

        when {
            response.isSuccessful && response.code() == 200 -> {
                Log.d(TAG, "검색바 응답 200 $response")
                "200"
            }
            response.isSuccessful && response.code() == 206 -> {
                Log.d(TAG, "검색바 응답 206 $response")
                "206"
            }
            else -> {
                Log.d(TAG, "기타 에러 $response")
                "Error"
            }
        }
    }
}
