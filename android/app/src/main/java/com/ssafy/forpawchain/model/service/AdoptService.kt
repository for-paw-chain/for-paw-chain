package com.ssafy.forpawchain.model.service

import android.util.Log
import com.google.gson.JsonObject
import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
import com.ssafy.forpawchain.viewmodel.activity.LoginVM
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdoptService {
    companion object {
        val TAG: String? = this::class.qualifiedName

        const val baseUrl: String = "http://j8a207.p.ssafy.io:3000/api/"
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        var service = retrofit.create(RetrofitService::class.java);
    }

    fun getAdoptList(): Call<JsonObject> {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.getAdoptList(0, null, null, null)

    }

    fun getDetailAdopt(pid: String): Call<JsonObject> {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.getDetailAdopt(pid)
    }

    fun deleteAdopt(pid: String): Call<ResponseBody> {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Access-Token", "Bearer qwerqwer")
                .build()
            chain.proceed(newRequest)
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(PetService.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.deleteAdopt(pid)
    }
}