package com.ssafy.forpawchain.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.io.OutputStream


class ImageSave {
    fun saveImageToGallery(
        context: Context,
        bitmap: Bitmap,
        title: String?,
        description: String?
    ): Uri? {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, title)
        values.put(MediaStore.Images.Media.DESCRIPTION, description)
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        var uri: Uri? = null
        try {
            uri = context.getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            val outputStream: OutputStream = uri?.let {
                context.getContentResolver().openOutputStream(
                    it
                )
            }
                ?: throw IOException("Failed to get output stream.")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
            outputStream.close()
            val message = "Image saved successfully."
            Log.d("MediaStore", message)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            if (uri != null) {
                context.getContentResolver().delete(uri, null, null)
            }
            val message = "Failed to save image: " + e.message
            Log.e("MediaStore", message)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
        return uri
    }
}