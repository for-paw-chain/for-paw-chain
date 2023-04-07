package com.ssafy.forpawchain.behind.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.room.Room
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.blockchain.ForPawChain
import com.ssafy.forpawchain.databinding.FragmentSearchResultBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.model.room.AppDatabase
import com.ssafy.forpawchain.model.room.UserInfo
import com.ssafy.forpawchain.model.room.UserInfo.Companion.token
import com.ssafy.forpawchain.model.service.AdoptService
import com.ssafy.forpawchain.model.service.AuthService
import com.ssafy.forpawchain.util.ImageLoader
import com.ssafy.forpawchain.viewmodel.fragment.SearchResultFragmentVM
import kotlinx.coroutines.*
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class SearchResultFragment : Fragment() {
    private lateinit var viewModel: SearchResultFragmentVM
    private var _binding: FragmentSearchResultBinding? = null
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
                val bundle = arguments
                bundle?.getSerializable("searchResultVM")?.let {
                    val code = (it as SearchResultDTO).code.toString()
                    lifecycleScope.launch {
                        AdoptService().getCA(code, UserInfo.token)
                            .enqueue(object :
                                Callback<JsonObject> {
                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성공된 경우

                                        var ca = response.body()?.get("content").toString()
                                        ForPawChain.setBlockChain(
                                            ca,
                                            user.privateKey
                                        )
                                        Log.d(TAG, "CA: " + ca)
                                        Log.d(TAG, "onResponse 성공");
                                    } else {
                                        Log.d(TAG, "CA onResponse 실패");

                                    }
                                }

                                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                                    Log.d(AdoptViewFragment.TAG, "네트워크 에러");

                                }
                            })
                    }
                }
            }
            else {
                lifecycleScope.launch {
                    val bundle = arguments
                    bundle?.getSerializable("searchResultVM")?.let {
                        val code = (it as SearchResultDTO).code.toString()
                        AdoptService().getCA(code, token)
                            .enqueue(object :
                                Callback<JsonObject> {
                                override fun onResponse(
                                    call: Call<JsonObject>,
                                    response: Response<JsonObject>
                                ) {
                                    if (response.isSuccessful) {
                                        // 정상적으로 통신이 성공된 경우
                                        var ca = response.body()?.get("content").toString()
                                        Log.d(AdoptViewFragment.TAG, "컨트랙트 주소 : " + ca)
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
                                    Log.d(AdoptViewFragment.TAG, "네트워크 에러");

                                }
                            })
                    }
                }
            }
        }

        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lateinit var searchResultDTO: SearchResultDTO
        navController = Navigation.findNavController(view)

        activity?.let {
            viewModel = ViewModelProvider(it).get(SearchResultFragmentVM::class.java)
            binding.lifecycleOwner = this

            val bundle = arguments
            bundle?.getSerializable("searchResultVM")?.let {
                searchResultDTO = it as SearchResultDTO
                binding.searchResultVM = searchResultDTO
            }

            //강아지의 주인인지
            //await() 함수를 사용하여 Deferred 객체를 해결해야 함
//            val auth : String = runBlocking {
//                getPetAuth(searchResultDTO.code).await() ?: ""
//            }

            var isPawInfoDetail : Boolean = false
            var isMatser : Boolean = false
            var isFriend : Boolean = false

            thread {

                val auth: String? = runBlocking { searchResultDTO.code?.let { it1 -> getPetAuth(it1) } }

                if (auth != null) {
                    auth.replace("\"", "")
                }
                isMatser = auth.equals("MASTER")
                isFriend = auth.equals("FRIEND")

                Log.d(TAG, searchResultDTO.code + " 권한 출력" + auth)
                Log.d(TAG,  "주인이니? " + isMatser)
                Log.d(TAG, "친구니? " + isFriend)

                //견적사항 있는지,
                isPawInfoDetail = !searchResultDTO.birth.isNullOrBlank()
                        || !searchResultDTO.region.isNullOrBlank()
                        || !searchResultDTO.tel.isNullOrBlank()
                        || !searchResultDTO.etc.isNullOrBlank()

            }.join()

            // 견적사항 있다 -> 견적사항 보여주기
            if (isPawInfoDetail) {
//                binding.idSearchResultDetail.visibility = View.GONE
                binding.idAddPawInfoDetailButton.visibility = View.GONE
            }
            // 견적사항 없다 + 주인이다 -> 견적사항 추가 버튼 보여주기
            else if (isMatser) {
                binding.idSearchResultDetail.visibility = View.GONE
//                binding.idAddPawInfoDetailButton.visibility = View.GONE
            }
            // 견적사항 없다 + 주인아니다 -> 아무것도 안 보여주기
            else {
                binding.idSearchResultDetail.visibility = View.GONE
                binding.idAddPawInfoDetailButton.visibility = View.GONE
            }

            // 의사아니다
//            if (!UserInfo.isDoctor) {
//                binding.fab.visibility = View.GONE
//            }

            //진료내역을 보여줄지 광고를 보여줄지
            // 권한 있거나 주인이거나 의사다

            if (isMatser || isFriend || UserInfo.isDoctor) {
                binding.idAdoptAd.visibility = View.GONE
            }

            // 의사도 아니고, 아무 권한도 없다 -> 의료내역 안보이고 유기동물 공고만 보인다.
            else {
                thread {
                    val adoptAd = runBlocking {getAdoptAd()}

                    val image = Glide.with(requireContext())
                        .load(adoptAd?.get("profile")?.asString)
                        .submit()
                        .get()

                    val adoptDTO : AdoptDTO = AdoptDTO(
                        MutableLiveData(adoptAd?.get("pid")?.asString ?: ""),
                        MutableLiveData(image),
                        MutableLiveData(if (adoptAd?.get("type")?.asString.equals("DOG")) "개과" else if(adoptAd?.get("type")?.asString.equals("CAT")) "고양이과" else "기타"),
                        MutableLiveData(adoptAd?.get("kind")?.asString),
                        MutableLiveData(if (adoptAd?.get("spayed")?.asString.equals("false")) "Ⅹ" else "○")
                    )

                    binding.adoptVM = adoptDTO
                }.join()

                binding.dignosisTitle.visibility = View.GONE
                binding.recycler.visibility = View.GONE
            }
        }

        // 검색 결과에서 의사일 경우 페이지 플로팅 액션 버튼 누르면
        // 진료기록 작성 페이지 이동

        binding.fab.setOnClickListener { view ->
            var bundle = Bundle()
            bundle.putString("code", searchResultDTO.code)
            navController.navigate(R.id.navigation_adopt_create, bundle)
        }

        val bundle = arguments
        bundle?.getSerializable("searchResultVM")?.let {
            binding.searchResultVM = it as SearchResultDTO
        }
        initObserve()

        navController = Navigation.findNavController(requireView())
        binding.idAddPawInfoDetailButton.setOnClickListener{view ->
            navController.navigate(com.ssafy.forpawchain.R.id.navigation_paw_info_create, bundle)
        }
        initObserve()
    }

    private fun initObserve() {
        viewModel.selectedSearchResult.observe(viewLifecycleOwner, Observer {
            binding.searchResultVM = it
            println("왜 안나와" + binding.searchResultVM)
        })
        viewModel.openEvent.eventObserve(this) { obj ->
            when (obj) {
                // TODO: navController
                ActivityCode.FRAGMENT_USER -> navController.navigate(com.ssafy.forpawchain.R.id.navigation_user)
                else -> {
                    null
                }
            }
        }
    }

    suspend fun getAdoptAd() : JsonObject? = withContext(Dispatchers.IO) {
        val response = AdoptService().getAdoptAd().execute()

        if (response.isSuccessful) {
            var items = response.body()?.get("content") as JsonArray

            // 정상적으로 통신이 성공된 경우
            Log.d(TAG, "onResponse 성공 광고 " + items.get(0).asJsonObject.toString()?: "")

            return@withContext items.get(0).asJsonObject

        } else {
            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
            Log.d(TAG, "onResponse 실패 " + response.errorBody()?.string())
            return@withContext null ?: null
        }
    }

    suspend fun getPetAuth(pid: String): String? = withContext(Dispatchers.IO){
        val token = UserInfo.token
        val response = AuthService().getPetAuth(pid, token).execute()

        Log.d(TAG, "UserInfo.token >> " + UserInfo.token + " , pId >> " + pid )

        if (response.isSuccessful) {
            // 정상적으로 통신이 성공된 경우
//            Log.d(TAG, "onResponse 성공1 " + response.toString() )
            Log.d(TAG, "onResponse 성공2 " + response.body()?.get("content").toString()?: "")
            return@withContext response.body()?.get("content").toString().replace("\"", "")?: ""

//            if (contentArray != null && contentArray.size() > 0) {
//                Log.d(TAG, "onResponse 성공3 " + contentArray[0].asString)
//                contentArray[0].asString
//            } else {
//                return@withContext ""
//            }
        } else {
            // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
            Log.d(TAG, "onResponse 실패 " + response.errorBody()?.string())
            return@withContext null ?: ""
        }
    }

//    suspend fun getPetAuth(pid: String) : String {
//        GlobalScope.launch {
//            val token = UserInfo.token
//            val response = withContext(Dispatchers.IO) {
//                AuthService().getPetAuth(pid, token)
//                    .enqueue(object :
//                        Callback<JsonObject> {
//                        override fun onResponse(
//                            call: Call<JsonObject>,
//                            response: Response<JsonObject>,
//                        ) {
//                            if (response.isSuccessful) {
//                                // 정상적으로 통신이 성공된 경우
//                                lifecycleScope.launch {
//                                    Log.d(TAG, "onResponse 성공1 " + response.toString())
//                                    Log.d(TAG, "onResponse 성공2 " + response.body()?.get("content")?.toString())
//                                    auth = response.body()?.get("content").toString()?: ""
//                                }
//                                // call
//
//                            } else {
//                                // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
//                                Log.d(TAG, "onResponse 실패 " + response.errorBody())
//                                auth = ""
//                            }
//                        }
//
//                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
//                            // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
//                            Log.d(MyPawFragment.TAG, "onFailure 에러: " + t.message.toString());
//                        }
//                    })
//            }
//        }
//        return ""
//    }

}

