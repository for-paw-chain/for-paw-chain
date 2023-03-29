package com.ssafy.forpawchain.behind.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ssafy.forpawchain.databinding.DialogAdopteeSetBinding
import com.ssafy.forpawchain.model.interfaces.IHandAdaptee
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete

class AdopteeSetDialog(context: Context, dialogInterface: IHandAdaptee) :
    Dialog(context) {
    private var mBinding: DialogAdopteeSetBinding? = null
    private val binding get() = mBinding!!

    private var dialogInterface: IHandAdaptee? = null

    init {
        this.dialogInterface = dialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogAdopteeSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doneBtn.setOnClickListener {
            // receiver가 내 자신 uid 와 동일하면 안됨.
            this.dialogInterface?.onHandPetBtnClick(binding.numberText.text.toString().toInt())
            dismiss()
        }
    }

}