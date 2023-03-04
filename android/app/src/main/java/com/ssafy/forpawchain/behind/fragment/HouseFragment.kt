package com.ssafy.forpawchain.behind.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.View.OnFocusChangeListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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
        Thread{
            binding.searchEditText.onFocusChangeListener =
                OnFocusChangeListener { v, gainFocus ->
                    //포커스가 주어졌을 때 동작
                    if (gainFocus) {
                        //to do
                        //원하는 동작
                    } else {
                        //원하는 동작
                    }
                }
        }
        val root: View = binding.root
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}