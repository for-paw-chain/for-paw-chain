package com.ssafy.forpawchain.model.service

import com.ssafy.forpawchain.model.service.retrofit.RetrofitService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdoptService {
    companion object {
        val TAG: String? = this::class.qualifiedName

        const val baseUrl: String = "http://70.12.246.152:8080"
        var retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        var service = retrofit.create(RetrofitService::class.java);
    }

    fun getAdoptList() {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java);

        return service.getAdoptList(0, null, null, null)
    }
}