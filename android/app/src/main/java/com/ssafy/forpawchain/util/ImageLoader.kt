package com.ssafy.forpawchain.util

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import java.net.URL

class ImageLoader {
    fun loadDrawableFromUrl(url: String, callback: (Drawable?) -> Unit) {
        object : AsyncTask<Void?, Void?, Drawable?>() {
            override fun doInBackground(vararg params: Void?): Drawable? {
                return try {
                    val inputStream = URL(url).openStream()
                    Drawable.createFromStream(inputStream, null)
                } catch (e: Exception) {
                    null
                }
            }

            override fun onPostExecute(result: Drawable?) {
                callback(result)
            }
        }.execute()
    }
}