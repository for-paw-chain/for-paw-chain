package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.ssafy.forpawchain.R
import com.ssafy.forpawchain.databinding.FragmentHouseBinding
import com.ssafy.forpawchain.viewmodel.fragment.HouseFragmentVM


class HouseFragment : Fragment() {
    private lateinit var viewModel: HouseFragmentVM
    private var _binding: FragmentHouseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        const val TAG: String = "HouseFragment"
    }

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHouseBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProvider(it).get(HouseFragmentVM::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
        }

        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}