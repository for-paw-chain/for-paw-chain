package com.ssafy.forpawchain.behind.dialog

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.ssafy.forpawchain.databinding.DialogQrCreateBinding


class QRCreateDialog(
    context: Context, pid: String,
    val qrImageSaveButton: (image: Bitmap) -> Unit
) :
    Dialog(context) {
    private var mBinding: DialogQrCreateBinding? = null
    private val binding get() = mBinding!!

    private val pid: String
    private lateinit var qrImage: Bitmap

    init {
        this.pid = pid
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DialogQrCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // QR 코드 생성하기
        // QR 코드 생성하기
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(pid, BarcodeFormat.QR_CODE, 512, 512)
            val bitmap =
                Bitmap.createBitmap(bitMatrix.width, bitMatrix.height, Bitmap.Config.RGB_565)
            for (x in 0 until bitMatrix.width) {
                for (y in 0 until bitMatrix.height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            qrImage = bitmap
            binding.qrImageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        binding.saveImageView.setOnClickListener {
            qrImageSaveButton.invoke(qrImage)
        }
    }
}