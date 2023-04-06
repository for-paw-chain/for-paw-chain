package com.ssafy.forpawchain.behind.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ssafy.forpawchain.databinding.DialogPermissionDeleteBinding
import com.ssafy.forpawchain.databinding.DialogPermissionSetBinding
import com.ssafy.forpawchain.model.interfaces.IAdoptCRUD
import com.ssafy.forpawchain.model.interfaces.IHandAdaptee
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete

class PermissionSetDialog(context: Context, dialogInterface: IHandAdaptee) :
    Dialog(context) {
    private var mBinding: DialogPermissionSetBinding? = null
    private val binding get() = mBinding!!

    private var dialogInterface: IHandAdaptee? = null

    init {
        this.dialogInterface = dialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogPermissionSetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.doneBtn.setOnClickListener {
            this.dialogInterface?.onHandPetBtnClick(binding.numberText.text.toString().toInt())
            dismiss()
        }
    }
}