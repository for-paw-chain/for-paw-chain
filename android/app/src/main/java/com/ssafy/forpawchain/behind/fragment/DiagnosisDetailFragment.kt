package com.ssafy.forpawchain.behind.fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
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
import com.ssafy.forpawchain.databinding.FragmentDiagnosisDetailBinding
import com.ssafy.forpawchain.databinding.FragmentPawBinding
import com.ssafy.forpawchain.model.domain.AdoptDTO
import com.ssafy.forpawchain.model.domain.MyPawListDTO
import com.ssafy.forpawchain.model.interfaces.IAdoptCRUD
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete
import com.ssafy.forpawchain.viewmodel.adapter.AdoptRecyclerViewAdapter
import com.ssafy.forpawchain.viewmodel.adapter.MyPawListAdapter
import com.ssafy.forpawchain.viewmodel.fragment.AdoptAddFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.DiagnosisDetailFragmentVM
import com.ssafy.forpawchain.viewmodel.fragment.PawFragmentVM

class DiagnosisDetailFragment : Fragment() {
    private var _binding: FragmentDiagnosisDetailBinding? = null
    private lateinit var viewModel: DiagnosisDetailFragmentVM
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
        _binding = FragmentDiagnosisDetailBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(DiagnosisDetailFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root

        return root
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