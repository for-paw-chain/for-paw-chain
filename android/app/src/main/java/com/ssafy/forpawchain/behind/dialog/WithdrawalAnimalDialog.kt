package com.ssafy.forpawchain.behind.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ssafy.forpawchain.databinding.DialogPermissionDeleteBinding
import com.ssafy.forpawchain.databinding.DialogWithdrawalAnimalBinding
import com.ssafy.forpawchain.databinding.DialogWithdrawalBinding
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete

class WithdrawalAnimalDialog(context: Context, dialogInterface: IPermissionDelete) :
    Dialog(context) {
    private var mBinding: DialogWithdrawalAnimalBinding? = null
    private val binding get() = mBinding!!

    private var dialogInterface: IPermissionDelete? = null

    init {
        this.dialogInterface = dialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogWithdrawalAnimalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.deleteBtn.setOnClickListener {
            this.dialogInterface?.onDeleteBtnClick()
            dismiss()

        }
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }
    }
}