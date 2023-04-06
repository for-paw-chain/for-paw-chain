package com.ssafy.forpawchain.behind.fragment

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.FragmentCreatePawInfoBinding
import com.ssafy.forpawchain.model.domain.SearchResultDTO
import com.ssafy.forpawchain.viewmodel.fragment.CreatePawInfoFragmentVM
import java.util.*

class CreatePawInfoFragment : Fragment() {
    private var _binding: FragmentCreatePawInfoBinding? = null
    private lateinit var viewModel: CreatePawInfoFragmentVM
    private lateinit var navController: NavController

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val TAG: String? = this::class.qualifiedName

        // 갤러리 권한 요청
        const val OPEN_GALLERY = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePawInfoBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(CreatePawInfoFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        initObserve()
        binding.imageView2.setOnClickListener() {
            // 이미지 선택을 위한 Intent 생성
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*" // 이미지 파일만 선택 가능하도록 설정
            startActivityForResult(intent, CreateDoctorHistoryFragment.OPEN_GALLERY)
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                var currentImageUrl: Uri? = data?.data
                try {
                    val imageUri = data?.data
                    val projection = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor = imageUri?.let {
                        requireActivity().contentResolver.query(
                            it,
                            projection,
                            null,
                            null,
                            null
                        )
                    }
                    cursor?.let {
                        if (it.moveToFirst()) {
                            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            val filePath = it.getString(columnIndex)
                            viewModel.path.postValue(filePath)

                        }
                        cursor.close()
                    }
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        currentImageUrl
                    )
                    binding.imageView2.setImageBitmap(bitmap)
                    Log.d(TAG, "이미지 불러오기 완료$currentImageUrl")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun initObserve() {
        viewModel.pawInfo.observe(viewLifecycleOwner, Observer {
            binding.searchResultVM = it
            println("왜 안나와" + binding.searchResultVM)
        })

        viewModel.openEvent.eventObserve(this) { obj ->


            when (obj) {
                ActivityCode.DONE -> {
                    navController.navigateUp()
                }
                else -> {
                    null
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProvider(it).get(CreatePawInfoFragmentVM::class.java)
            binding.lifecycleOwner = this
        }

        val bundle = arguments
        bundle?.getSerializable("searchResultVM")?.let {
            binding.searchResultVM = it as SearchResultDTO
        }

        initObserve()

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}