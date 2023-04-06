package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.behind.dialog.QRCreateDialog
import com.ssafy.forpawchain.databinding.FragmentHouseBinding
import com.ssafy.forpawchain.databinding.FragmentSearchResultBinding
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.model.room.UserInfo.Companion.token
import com.ssafy.forpawchain.model.service.PetService
import com.ssafy.forpawchain.util.ImageLoader
import com.ssafy.forpawchain.util.ImageSave
import com.ssafy.forpawchain.util.PreferenceManager
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class HouseFragment : Fragment() {
    private lateinit var viewModel: HouseFragmentVM
    private var _binding: FragmentHouseBinding? = null
    private lateinit var navController: NavController
    private lateinit var response : retrofit2.Response<JsonObject>
    private lateinit var searchResultBinding: FragmentSearchResultBinding

    private var imageResId: Int = 0
    private lateinit var defaultDrawable: Drawable
    private lateinit var petDefaultImg: MutableLiveData<Drawable>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 기본 견적 사항 이미지 파일 가져오기
        imageResId = resources.getIdentifier("icon_default_pet", "drawable", context.packageName)

        // Drawable 객체 생성
        defaultDrawable = ContextCompat.getDrawable(requireContext(), imageResId)!!
        petDefaultImg = MutableLiveData(defaultDrawable)
    }
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

        token = PreferenceManager().getString(requireActivity(), "token")!!
        Log.d(TAG, "HouseFragment 토큰 : ${token}")



        // 값 할당
//        petImageLiveData.postValue(defaultDrawable)

//        petImageLiveData.observe(viewLifecycleOwner) { petImage: Drawable? ->
//            val drawable: Drawable = petImage ?: defaultDrawable

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

                val code = it.code.value!!
                doSearching(code)
            })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        viewModel.todoLiveData.observe(
            requireActivity(),
            Observer { //viewmodel에서 만든 변경관찰 가능한todoLiveData를 가져온다.
                (binding.recycler.adapter as MyPawListAdapter).setData(it) //setData함수는 TodoAdapter에서 추가하겠습니다.
            })

        /**
         * "E/ImeBackDispatcher: Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: XXX"
         * 에러가 발생하는 경우에는 일반적으로 소프트 키보드에 대한 이벤트 처리 관련 문제가 발생한 경우
         * 이 에러는 문제를 일으키지 않지만 사용자에게 혼란을 주거나 로그를 지저분하게 함
         * 해결책
         * IME 액션 처리를 수행하는 EditText의 소프트 키보드 이벤트 리스너를 등록할 때,
         * 이벤트 리스너를 등록하는 부분을 UI 스레드에서 실행하도록 변경
         * binding.searchEditText.post를 사용하여 UI 스레드에서 실행되도록 코드 변경
         **/

        //동물 등록 검색 번호 창 번호 입력 버튼
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            var input = binding.searchEditText.text.toString();

            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    // 엔터키가 눌렸을 때 처리할 코드 작성
                    doSearching(input)
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

    private fun doSearching(input: String) {
        GlobalScope.launch {
            when (getPetInfo(input, token)) {
                "200" -> {
                    Log.d(TAG, "response 객체 내부는 = ${response.body()}")

                    val profileUrl = response.body()!!.get("profile").asString
                    // Glide로 이미지 다운로드 및 ImageView에 로딩
                    val drawable = Glide.with(requireContext())
                        .load(profileUrl)
                        .submit()
                        .get()

                    val birthStr = response.body()!!.get("birth")?.asString
                    val birthDate: Date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(birthStr)
                    val diffInMillis = System.currentTimeMillis() - birthDate!!.time
                    val diffInYears = TimeUnit.MILLISECONDS.toDays(diffInMillis) / 365

                    val neutered = response.body()!!.get("spayed").asString
                    val species = response.body()!!.get("type").asString
                    val sex = response.body()!!.get("sex").asString

                    val searchResultDTO = SearchResultDTO(
                        code = response.body()!!.get("pid").asString ?: "",
                        profile = drawable,
                        name = response.body()!!.get("name").asString ?: "",
                        sex =  if (sex.equals("MALE")) "남아" else "여아",
                        species = if (species.equals("CAT")) "고양이과" else if (species.equals("DOG")) "개과" else "기타",
                        kind = response.body()!!.get("kind").asString ?: "",
                        neutered = if (neutered.equals("false")) "Ⅹ" else if (neutered.equals("true")) "○" else "",
                        birth = "$diffInYears 살",
                        region = response.body()?.get("region")?.asString,
                        tel = response.body()?.get("tel")?.asString,
                        etc = response.body()?.get("etc")?.asString
                    )

                    /**
                     * setCurrentState 메서드는 메인 스레드에서 호출해야함.
                     * 그러나 현재 setCurrentState가 호출되는 시점은 GlobalScope.launch 블록 내부에서 실행 중인 백그라운드 스레드에서입니다.
                     * 따라서 navController.navigate 메서드를 메인 스레드에서 실행되도록 withContext(Dispatchers.Main)안에서 사용되게 함
                     **/

                    withContext(Dispatchers.Main) {
                        var bundle = Bundle()
                        bundle.putSerializable("searchResultVM", searchResultDTO)
                        val navController = Navigation.findNavController(requireView())
                        navController.navigate(R.id.navigation_search_result, bundle)
                    }
                }
                "206" -> {
                    Log.d(TAG, "response 객체 내부는 = ${response.body()}")

                    val neutered = response.body()!!.get("spayed").asString
                    val species = response.body()!!.get("type").asString
                    val sex = response.body()!!.get("sex").asString

                    val searchResultDTO = SearchResultDTO(
                        code = response.body()!!.get("pid").asString ?: "",
                        profile = defaultDrawable,
                        name = response.body()!!.get("name").asString ?: "",
                        sex =  if (sex.equals("MALE")) "남아" else "여아",
                        species = if (species.equals("CAT")) "고양이과" else if (species.equals("DOG")) "개과" else "기타",
                        kind = response.body()!!.get("kind").asString ?: "",
                        neutered = if (neutered.equals("false")) "Ⅹ" else if (neutered.equals("true")) "○" else "",
                        birth = "",
                        region = "",
                        tel = "",
                        etc = ""
                    )

                    withContext(Dispatchers.Main) {
                        var bundle = Bundle()
                        bundle.putSerializable("searchResultVM", searchResultDTO)
                        val navController = Navigation.findNavController(requireView())
                        navController.navigate(R.id.navigation_search_result, bundle)
                    }
                    //return@launch // launch 블록에서 반환하여 함수를 빠져나감
                }
                else -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            requireContext(),
                            "${input}은 없는 동물입니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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

                    PetService().getMyPets(token).enqueue(object :
                        Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                            if (response.isSuccessful) {
                                // 정상적으로 통신이 된 경우
                                var result: JsonObject? = response.body()
                                val items = result?.get("content") as JsonArray
                                if (result != null) {
                                    for (item in items.asJsonArray) {
                                        var item1 = item as JsonObject
                                        if (item1["profile"].toString() == "null") { // 프로필이 없는 경우
                                            viewModel.addTask(MyPawListDTO(
                                                MutableLiveData<String>(item1["pid"].asString),
                                                petDefaultImg,
                                                MutableLiveData<String>(item1["name"].asString),
                                                MutableLiveData<String>(if (item1["sex"].asString.equals("MALE")) "남아" else "여아"),
                                                MutableLiveData<String>(if (item1["type"].asString.equals("DOG")) "개과" else if (item1["type"].asString.equals("CAT")) "고양이과" else "기타"),
                                                MutableLiveData<String>(item1["kind"].asString),
                                                // false가 중성화 안함, true가 중성화 함
                                                MutableLiveData<String>(if (item1["spayed"].asString.equals("false")) "Ⅹ" else if(item1["spayed"].asString.equals("true")) "○" else ""),
                                            ))
                                        } else { // 프로필이 있는 경우
                                            ImageLoader().loadDrawableFromUrl(item1["profile"].asString) { drawable ->
                                                viewModel.addTask(
                                                    MyPawListDTO(
                                                        MutableLiveData<String>(item1["pid"].asString),
                                                        MutableLiveData<Drawable>(drawable),
                                                        MutableLiveData<String>(item1["name"].asString),
                                                        MutableLiveData<String>(if (item1["sex"].asString.equals("MALE")) "남아" else "여아"),
                                                        MutableLiveData<String>(if (item1["type"].asString.equals("DOG")) "개과" else if (item1["type"].asString.equals("CAT")) "고양이과" else "기타"),
                                                        MutableLiveData<String>(item1["kind"].asString),
                                                        MutableLiveData<String>(if (item1["spayed"].asString.equals("false")) "Ⅹ" else if(item1["spayed"].asString.equals("true")) "○" else ""),
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

    suspend fun getPetInfo(input: String, token: String): String = withContext(Dispatchers.IO) {
        response = PetService().getPetInfo(input, token).execute()

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
