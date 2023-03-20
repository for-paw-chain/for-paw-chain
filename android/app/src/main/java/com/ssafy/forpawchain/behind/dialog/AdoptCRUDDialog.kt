package com.ssafy.forpawchain.behind.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.ssafy.forpawchain.databinding.DialogAdoptCrudBinding
import com.ssafy.forpawchain.databinding.DialogPermissionDeleteBinding
import com.ssafy.forpawchain.model.interfaces.IAdoptCRUD
import com.ssafy.forpawchain.model.interfaces.IPermissionDelete

class AdoptCRUDDialog(context: Context, dialogInterface: IAdoptCRUD) :
    Dialog(context) {
    private var mBinding: DialogAdoptCrudBinding? = null
    private val binding get() = mBinding!!

    private var dialogInterface: IAdoptCRUD? = null

    init {
        this.dialogInterface = dialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogAdoptCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.updateBtn.setOnClickListener {
            this.dialogInterface?.onUpdateBtnClick()
            dismiss()
        }

        binding.deleteBtn.setOnClickListener {
            this.dialogInterface?.onDeleteBtnClick()
            dismiss()

        }

    }
}