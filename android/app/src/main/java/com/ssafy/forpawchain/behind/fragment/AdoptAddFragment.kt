package com.ssafy.forpawchain.behind.fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ssafy.basictemplate.util.ActivityCode
import com.ssafy.basictemplate.util.eventObserve
import com.ssafy.forpawchain.behind.activity.LoginActivity
import com.ssafy.forpawchain.behind.activity.MainActivity
import com.ssafy.forpawchain.behind.dialog.AdoptCRUDDialog
import com.ssafy.forpawchain.behind.dialog.PermissionDialog
import com.ssafy.forpawchain.databinding.FragmentAdoptAddBinding
import com.ssafy.forpawchain.databinding.FragmentPawBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IAdoptCRUD
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.viewmodel.adapter.AdoptRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.fragment.AdoptAddFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.PawFragmentVM

class AdoptAddFragment : Fragment() {
    private var _binding: FragmentAdoptAddBinding? = null
    private lateinit var viewModel: AdoptAddFragmentVM
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
        _binding = FragmentAdoptAddBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(AdoptAddFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        initObserve()
        binding.imageView1.setOnClickListener() {
            // 이미지 선택을 위한 Intent 생성
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
            if (requestCode == CreateDoctorHistoryFragment.OPEN_GALLERY) {
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
                    binding.imageView1.setImageBitmap(bitmap)
                    Log.d(TAG, "이미지 불러오기 완료$currentImageUrl")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun initObserve() {
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
        navController = Navigation.findNavController(requireView())

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}